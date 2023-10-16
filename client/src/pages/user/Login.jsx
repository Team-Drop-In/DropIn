import styled from "styled-components";
import { useState } from "react";
import { useNavigate, Link } from "react-router-dom";
import { Controller, useForm } from "react-hook-form";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import Input from "../../components/common/Input";
import Button from "../../components/common/Button";
import LogoImage from "../../images/logo.svg";
import google from "../../images/google.svg";
import { loginApi, googleloginApi } from "../../apis/api";
import { loginState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";

const Login = () => {
  const [error, setError] = useState(false);
  const [errorMsg, setErrorMsg] = useState("");
  const setLogin = useSetRecoilState(loginState);
  const navigate = useNavigate();

  const {
    handleSubmit,
    control,
    formState: { isValid },
  } = useForm();

  const emailOptions = {
    required: "이메일을 입력해주세요.",
    pattern: {
      value:
        /^[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/i,
      message: "@를 포함한 이메일 주소를 적어주세요.",
    },
  };

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
    loginApi(data)
      .then(() => {
        navigate("/");
        setLogin(true);
      })
      .catch((error) => {
        if (error && error.status === 401) {
          setErrorMsg("가입 정보가 없습니다");
          setError(true);
        } else {
          setError(true);
          setErrorMsg("로그인에 실패했습니다 다시 시도해주세요");
        }
      });
  };

  const handleGoogleLogin = () => {
    googleloginApi();
    const currentPath = window.location.pathname;
    console.log(currentPath);
  };

  return (
    <Wrap>
      <Contain>
        <Logo src={LogoImage} alt="로고" className="logo_img" />
        <Form onSubmit={handleSubmit(onFormSubmit)}>
          <Controller
            name={"username"}
            control={control}
            rules={emailOptions}
            render={({ field, fieldState: { error } }) => (
              <Input
                id="username"
                label="이메일"
                type="text"
                placeholder="이메일"
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
                placeholder="비밀번호"
                errorMessage={error?.message}
                onChange={field.onChange}
                value={field.value || ""}
              />
            )}
          />
          {error && <ErrorMsg>{errorMsg}</ErrorMsg>}
          <Button
            text={"로그인"}
            width={"100%"}
            height={"40px"}
            style={{
              backgroundColor: isValid
                ? `${COLOR.main_yellow}`
                : `${COLOR.btn_grey}`,
              cursor: isValid ? "pointer" : "default",
              marginTop: "10px",
            }}
            type="submit"
          />
        </Form>
        <More>
          <Find>
            {/* <Link to="/findemail">아이디</Link> /{" "} */}
            <Link to="/findpwd">비밀번호</Link> 찾기
          </Find>
          <Google>
            <GoogleBtn onClick={handleGoogleLogin}>
              <img src={google} alt="구글 로그인" />
            </GoogleBtn>
            <span>Google로 시작하기</span>
          </Google>
        </More>
      </Contain>
    </Wrap>
  );
};

export default Login;

const Wrap = styled(Container)`
  label {
    display: none;
  }
`;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const Logo = styled.img`
  width: 180px;
`;

const Form = styled.form`
  width: 350px;
  margin-top: 35px;
  margin-bottom: 12px;
`;

const ErrorMsg = styled.div`
  width: 100%;
  padding: 0 2px;
  text-align: right;
`;

const More = styled.div`
  width: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;
const Find = styled.article`
  font-size: 14px;
`;
const Google = styled.div`
  width: 350px;
  margin-top: 40px;
  text-align: center;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;

  span {
    color: white;
    margin-top: 7px;
    font-size: 14px;
  }
`;

const GoogleBtn = styled.button`
  width: 50px;
  height: 50px;
  border: none;
  background-color: transparent;
  cursor: pointer;

  img {
    width: 50px;
    height: 50px;
    object-fit: contain;
  }
`;
