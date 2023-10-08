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
    public void createBoxTag(List<String> boxTagNameList, Long boxId){
        Box box = boxRepository.findById(boxId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
        if(boxTagNameList != null) {
            for (String boxTagName : boxTagNameList) {
                BoxTag newBoxTag = new BoxTag(boxTagName);
                newBoxTag.addBox(box);
                boxTagRepository.save(newBoxTag);
            }
        }
    }

    @Transactional(readOnly = false)
    public void updateAndDeleteBoxTag(List<String> updateBoxTagNameList, Long boxId){
        Box box = boxRepository.findById(boxId).orElseThrow(() -> new BusinessLogicException(ExceptionCode.BOX_NOT_FOUND));
        if(updateBoxTagNameList == null){
            boxTagRepository.deleteAllByBoxId(boxId);
        } else {
            List<BoxTag> currentBoxTagList = box.getBoxTagList();
            List<String> currentBoxTagNameList = currentBoxTagList.stream().map(BoxTag::getTagName).collect(Collectors.toList());
            for (String newBoxTagName : updateBoxTagNameList) {
                if (!currentBoxTagNameList.contains(newBoxTagName)) {
                    BoxTag newBoxTag = new BoxTag(newBoxTagName);
                    newBoxTag.addBox(box);
                    boxTagRepository.save(newBoxTag);
                }
            }
            for (BoxTag currentBoxTag : currentBoxTagList) {
                if (!updateBoxTagNameList.contains(currentBoxTag.getTagName())) {
                    boxTagRepository.delete(currentBoxTag);
                }
            }
        }
    }
}
