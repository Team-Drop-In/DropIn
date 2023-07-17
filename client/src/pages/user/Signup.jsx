import styled from "styled-components";
import { useNavigate, Link } from "react-router-dom";
import { Controller, useForm } from "react-hook-form";
import { container, content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";
import LogoImage from "../../images/logo.svg";

const Signup = () => {
  const navigate = useNavigate();
  const { handleSubmit, control } = useForm();

  const emailOptions = {
    required: "이메일을 입력해주세요.",
    pattern: {
      value:
        /^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i,
      message: "@를 포함한 이메일 주소를 적어주세요.",
    },
  };
  const passwordOptions = {
    required: "비밀번호를 입력해주세요.",
    pattern: {
      value:
        /^(?=.*[A-Z])(?=.*[a-z])(?=.*\d)(?=.*[@$!%*#?&])[A-Za-z\d@$!%*#?&]{8,}$/,
      message:
        "비밀번호는 8자 이상으로 하나 이상의 대문자, 소문자, 숫자, 특수문자를 포함해주세요.",
    },
  };

  return (
    <Container>
      <Content>
        <Logo src={LogoImage} alt="로고" className="logo_img" />
        <Title>회원가입</Title>
        {/* <Form onSubmit={handleSubmit(onFormSubmit, onFormError)}> */}
        <Form>
          <Controller
            name={"email"}
            control={control}
            rules={emailOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                id="email"
                label="이메일"
                type="text"
                placeholder="이메일을 입력해 주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"password"}
            control={control}
            rules={passwordOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="비밀번호"
                type="password"
                placeholder="비밀번호를 입력해 주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"passwordcheck"}
            control={control}
            rules={passwordOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="비밀번호 확인"
                type="passwordcheck"
                placeholder="비밀번호를 한번 더 입력해 주세요"
                errorMessage={error?.message}
              />
            )}
          />
          <Controller
            name={"name"}
            control={control}
            rules={emailOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                id="name"
                label="이름"
                type="text"
                placeholder="이름을 입력해 주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"nickname"}
            control={control}
            rules={emailOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                id="nickname"
                label="닉네임"
                type="text"
                placeholder="닉네임을 입력해 주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Button
            text={"회원가입"}
            width={"100%"}
            height={"40px"}
            style={{ marginTop: "10px" }}
            type="submit"
          />
        </Form>
      </Content>
    </Container>
  );
};

export default Signup;

const Container = styled(container)`
  label {
    font-size: 14px;
  }
`;

const Content = styled(content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const Logo = styled.img`
  width: 220px;
`;

const Title = styled.span`
  margin-top: 12px;
  font-size: 20px;
  color: ${COLOR.main_grey};
`;
const Form = styled.form`
  width: 350px;
  margin-top: 35px;
  margin-bottom: 12px;
`;
