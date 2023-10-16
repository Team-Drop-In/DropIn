import styled from "styled-components";
import { useState } from "react";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";
import { useForm, Controller } from "react-hook-form";
import { useNavigate } from "react-router-dom";
import { loginState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";
import { changePwdApi } from "../../apis/api";

const ChangePwd = () => {
  const navigate = useNavigate();
  const setLogin = useSetRecoilState(loginState);
  const [error, setIsError] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");

  const {
    handleSubmit,
    control,
    getValues,
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

  const passwordCheckOptions = {
    required: "비밀번호를 한번 더 입력해주세요",
    validate: (value) => {
      const password = getValues("updatePassword");
      if (value === password) {
        return true;
      } else {
        return "비밀번호와 일치하지 않습니다";
      }
    },
  };

  const onFormSubmit = async (data) => {
    try {
      console.log(data);
      await changePwdApi(data);
      localStorage.removeItem("accessToken");
      localStorage.removeItem("accessTokenExpiration");
      setLogin(false);
      navigate("/login");
    } catch (error) {
      if (error.data.status === 403) {
        setIsError(true);
        setErrorMsg("비밀번호가 일치하지 않습니다");
      }
    }
  };

  return (
    <Container>
      <Contain>
        <Title>
          <h2>비밀번호 변경</h2>
          <p>현재 비밀번호와 새 비밀번호를 입력해주세요</p>
        </Title>

        <Form onSubmit={handleSubmit(onFormSubmit)}>
          <Controller
            name={"currentPassword"}
            control={control}
            rules={passwordOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="현재 비밀번호"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"updatePassword"}
            control={control}
            rules={passwordOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="새 비밀번호"
                type="password"
                placeholder="비밀번호를 입력해주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          <Controller
            name={"updatePasswordCheck"}
            control={control}
            rules={passwordCheckOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                label="비밀번호 확인"
                type="password"
                placeholder="비밀번호를 한번 더 입력해주세요"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          {errors.form && <ErrorMsg>일치하는 정보가 없습니다</ErrorMsg>}
          {error && <ErrorMsg>{errorMsg}</ErrorMsg>}
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

export default ChangePwd;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
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
  color: ${COLOR.btn_grey};
  margin-top: 4px;
`;
