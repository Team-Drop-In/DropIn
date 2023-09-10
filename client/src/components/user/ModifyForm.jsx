import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { GiMale } from "react-icons/gi";
import Input from "../common/Input";
import Button from "../common/Button";
import ImageUpload from "./ImageUpload";
import { AiOutlineRight } from "react-icons/ai";
import { Controller, useForm } from "react-hook-form";
import { Link } from "react-router-dom";
import { useState } from "react";
import { duplicateNicknameApi } from "../../apis/api";

const ModifyForm = ({ setChangeInfo }) => {
  const [nicknameValue, setNicknameValue] = useState("");
  const [isNicknameAvailable, setIsNicknameAvailable] = useState(false);
  const [imageUrl, setImageUrl] = useState(null);
  const [imageData, setImageData] = useState(new FormData());

  const {
    handleSubmit,
    control,
    formState: { isValid },
  } = useForm();

  const nickValidationOptions = {
    required: "닉네임을 입력해주세요.",
    minLength: {
      value: 2,
      message: "닉네임은 최소 2글자 이상이어야 합니다.",
    },
    maxLength: {
      value: 20,
      message: "닉네임은 최대 20글자까지 가능합니다.",
    },
    pattern: {
      value: /^[A-Za-z가-힣]+$/,
      message: "닉네임은 한글과 영어만 사용 가능합니다.",
    },
    validate: (value) => {
      if (/\s/.test(value)) {
        return "닉네임에 공백을 포함할 수 없습니다.";
      }
      return true;
    },
  };

  const handleNicknameAvailability = async () => {
    const validateResult = nickValidationOptions.validate(nicknameValue);
    if (!nicknameValue.trim()) return;
    if (nickValidationOptions.minLength.value > nicknameValue.length) return;
    if (nickValidationOptions.maxLength.value < nicknameValue.length) return;
    if (!nickValidationOptions.pattern.value.test(nicknameValue)) return;
    if (validateResult !== true) return;

    try {
      await duplicateNicknameApi({ nickname: nicknameValue });
      setIsNicknameAvailable(true);
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  const onFormSubmit = async () => {
    console.log(nicknameValue);
  };

  return (
    <>
      <User>
        <Img>
          <ImageUpload
            imageUrl={imageUrl}
            setImageUrl={setImageUrl}
            imageData={imageData}
            setImageData={setImageData}
          />
          <Username>
            <span>닉네임</span>
            <GiMale size={20} color={COLOR.gender_blue} />
          </Username>
        </Img>
        <Info>
          <div>
            <Label>이메일</Label>
            <span>Test@gmail.com</span>
          </div>
          <div>
            <Label>이름</Label>
            <span>테스트</span>
          </div>
          <Form onSubmit={handleSubmit(onFormSubmit)}>
            <Controller
              name={"nickname"}
              control={control}
              rules={nickValidationOptions}
              render={({ field, fieldState: { error } }) => (
                <Input
                  id="nickname"
                  label="닉네임"
                  type="text"
                  width={"300px"}
                  placeholder="닉네임을 입력해 주세요"
                  errorMessage={error?.message}
                  onChange={(e) => {
                    field.onChange(e.target.value);
                    setNicknameValue(e.target.value);
                  }}
                  value={field.value || ""}
                />
              )}
            />
            <Button
              text={"중복확인"}
              type="button"
              width={"110px"}
              height={"39px"}
              style={{
                marginTop: "20px",
                marginLeft: "5px",
                backgroundColor: isNicknameAvailable
                  ? ` ${COLOR.main_yellow}`
                  : ` ${COLOR.btn_grey}`,
              }}
              disabled={!nicknameValue.trim()}
              onClick={handleNicknameAvailability}
            />
          </Form>
        </Info>
      </User>
      <ButtonWrapper>
        <Button
          text={"수정완료"}
          type="submit"
          width={"100%"}
          height={"39px"}
          style={{
            backgroundColor: ` ${COLOR.main_yellow}`,
            cursor: isValid ? "pointer" : "default",
          }}
        />
        <StyledLink to="/changepwd">
          <span>비밀번호 변경</span>
          <AiOutlineRight />
        </StyledLink>
      </ButtonWrapper>
    </>
  );
};

export default ModifyForm;

const User = styled.section`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
  margin-bottom: 30px;
`;

const Img = styled.div`
  width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  padding: 20px;
`;

const Username = styled.div`
  margin-top: 20px;
  display: flex;
  align-items: center;

  span {
    color: ${COLOR.main_grey};
    margin-right: 3px;
  }
`;

const Info = styled.div`
  width: 400px;
  padding: 20px;

  & > div {
    margin-bottom: 25px;
  }

  & > div > span {
    color: ${COLOR.main_grey};
  }
`;

const Label = styled.div`
  margin-bottom: 10px;
`;

const Form = styled.form`
  display: flex;
  align-items: flex-end;

  button {
    margin-bottom: 9px;
  }
`;

const ButtonWrapper = styled.div`
  margin-top: 20px;
  width: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  button {
    margin-bottom: 10px;
  }
  span {
    color: ${COLOR.main_grey};
  }
`;

const StyledLink = styled(Link)`
  display: flex;
  align-items: center;
`;
