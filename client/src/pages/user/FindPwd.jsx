import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { useState } from "react";
import { useForm, Controller } from "react-hook-form";

const FindPwd = () => {
  const {
    handleSubmit,
    control,
    formState: { errors },
  } = useForm();

  const emailValidationOptions = {
    required: "이메일을 입력해주세요.",
    pattern: {
      value: /\S+@\S+\.\S+/,
      message: "올바른 이메일 형식이 아닙니다.",
    },
  };

  const nameValidationOptions = {
    required: "이름을 입력해주세요.",
    minLength: {
      value: 2,
      message: "이름은 최소 2글자 이상이어야 합니다.",
    },
    maxLength: {
      value: 10,
      message: "이름은 최대 10글자까지 가능합니다.",
    },
    pattern: {
      value: /^[^\s]+$/,
      message: "이름에 공백을 포함할 수 없습니다.",
    },
  };

  const onFormSubmit = (data) => {
    // 폼 제출 로직
  };

  return (
    <Container>
      <Contain>
        <form onSubmit={handleSubmit(onFormSubmit)}>
          <Controller
            name="email"
            control={control}
            rules={emailValidationOptions}
            render={({ field }) => (
              <div>
                <label htmlFor="email">이메일</label>
                <input {...field} type="text" placeholder="이메일" />
                {errors.email && <ErrorMsg>{errors.email.message}</ErrorMsg>}
              </div>
            )}
          />
          <Controller
            name="name"
            control={control}
            rules={nameValidationOptions}
            render={({ field }) => (
              <div>
                <label htmlFor="name">이름</label>
                <input {...field} type="text" placeholder="이름" />
                {errors.name && <ErrorMsg>{errors.name.message}</ErrorMsg>}
              </div>
            )}
          />
          <button type="submit">확인</button>
          {errors.form && <ErrorMsg>일치하는 정보가 없습니다.</ErrorMsg>}
        </form>
      </Contain>
    </Container>
  );
};

export default FindPwd;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const ErrorMsg = styled.div`
  color: red;
  font-size: 12px;
  margin-top: 4px;
`;
