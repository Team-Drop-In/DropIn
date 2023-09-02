import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";
import { useForm, Controller } from "react-hook-form";

const Leave = () => {
  const {
    handleSubmit,
    control,
    formState: { errors, isValid },
  } = useForm();

  const passwordOptions = {
    required: "비밀번호를 입력해주세요",
    validate: (value) => {
      if (value.length < 8) {
        return "비밀번호는 8자 이상이어야 합니다";
      }

      const hasUppercase = /[A-Z]/.test(value);
      const hasLowercase = /[a-z]/.test(value);
      const hasNumber = /[0-9]/.test(value);
      const hasSpecialChar = /[!@#$%^&()]/.test(value);

      if (!(hasUppercase && hasLowercase && hasNumber && hasSpecialChar)) {
        return "대/소문자, 숫자, 특수문자를 포함해야 합니다";
      }

      return true;
    },
  };

  const onFormSubmit = (data) => {
    // 폼 제출 로직
  };

  return (
    <Container>
      <Contain>
        <Title>
          <h2>회원 탈퇴</h2>
          <p>비밀번호를 입력해주세요</p>
        </Title>

        <Form onSubmit={handleSubmit(onFormSubmit)}>
          <Controller
            name={"password"}
            control={control}
            rules={passwordOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="비밀번호"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"passwordcheck"}
            control={control}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="비밀번호 확인"
                type="password"
                placeholder="비밀번호를 한번 더 입력해주세요"
                errorMessage={error?.message}
              />
            )}
          />
          {errors.form && <ErrorMsg>일치하는 정보가 없습니다</ErrorMsg>}
          <Button
            type="submit"
            text={"확인"}
            height={"39px"}
            margin={"10px 0 0 0"}
            disabled={!isValid}
            style={{
              backgroundColor: isValid
                ? `${COLOR.main_yellow}`
                : `${COLOR.btn_grey}`,
              cursor: isValid ? "pointer" : "default",
            }}
          />
        </Form>
      </Contain>
    </Container>
  );
};

export default Leave;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const Title = styled.div`
  text-align: center;
  margin-bottom: 30px;
  width: 350px;

  h2 {
    color: ${COLOR.main_grey};
    font-size: 26px;
    font-weight: bold;
    letter-spacing: 1px;
    margin-bottom: 10px;
  }

  p {
    color: white;
    font-size: 15px;
    letter-spacing: 0.5px;
  }
`;

const Form = styled.form`
  width: 350px;
  margin-top: 15px;
`;

const ErrorMsg = styled.div`
  color: ${COLOR.main_yellow};
  font-size: 12px;
  margin-top: 4px;
`;
