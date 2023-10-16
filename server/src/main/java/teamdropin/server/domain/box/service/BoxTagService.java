package teamdropin.server.domain.box.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import teamdropin.server.domain.box.dto.boxImage.CreateBoxImageRequestDto;
import teamdropin.server.domain.box.dto.boxTag.CreateBoxTagRequestDto;
import teamdropin.server.domain.box.dto.boxTag.UpdateBoxTagRequestDto;
import teamdropin.server.domain.box.entity.Box;
import teamdropin.server.domain.box.repository.BoxRepository;
import teamdropin.server.domain.box.entity.BoxTag;
import teamdropin.server.domain.box.repository.BoxTagRepository;
import teamdropin.server.global.exception.BusinessLogicException;
import teamdropin.server.global.exception.ExceptionCode;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
@RequiredArgsConstructor
public class BoxTagService {

    private final BoxTagRepository boxTagRepository;
    private final BoxRepository boxRepository;

    @Transactional(readOnly = false)
    public void createBoxTag(List<CreateBoxTagRequestDto> boxTagNameList, Long boxId){
        Box box = boxRepository.findById(boxId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));

        if(boxTagNameList != null) {
            boxTagNameList.stream()
                    .map(CreateBoxTagRequestDto::getTagName)
                    .map(BoxTag::new)
                    .peek(newBoxTag -> newBoxTag.addBox(box))
                    .forEach(boxTagRepository::save);
        }
    }

    @Transactional(readOnly = false)
    public void updateAndDeleteBoxTag(List<UpdateBoxTagRequestDto> updateBoxTagRequestDtoList, Long boxId){
        Box box = boxRepository.findById(boxId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
        if(updateBoxTagRequestDtoList == null){
            boxTagRepository.deleteAllByBoxId(boxId);
        } else {
            List<BoxTag> currentBoxTagList = box.getBoxTagList();
            List<String> currentBoxTagNameList = currentBoxTagList.stream().map(BoxTag::getTagName).collect(Collectors.toList());
            List<String> updateBoxTagNameList = updateBoxTagRequestDtoList.stream().map(UpdateBoxTagRequestDto::getTagName).collect(Collectors.toList());

            updateBoxTagNameList.stream()
                    .filter(newBoxTagName -> !currentBoxTagNameList.contains(newBoxTagName))
                    .map(BoxTag::new)
                    .peek(newBoxTag -> newBoxTag.addBox(box))
                    .forEach(boxTagRepository::save);

            currentBoxTagList.stream()
                    .filter(currentBoxTag -> !updateBoxTagNameList.contains(currentBoxTag.getTagName()))
                    .forEach(boxTagRepository::delete);
        }
    }
}
