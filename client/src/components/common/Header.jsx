import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import LogoImage from "../../images/logo.svg";
import { BiSolidUser } from "react-icons/bi";
import { useNavigate, Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { loginState } from "../../atoms/atom";
import { ModalState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";

import { COLOR } from "../../styles/theme";

const Header = () => {
  const navigate = useNavigate();
  const isLogin = useRecoilValue(loginState);
  const setModal = useSetRecoilState(ModalState);

  const handleModal = () => {
    setModal((prev) => !prev);
  };

  return (
    <>
      <Wrap>
        <Contain>
          <Tab>
            <button onClick={() => navigate("/")}>
              <Logo src={LogoImage} alt="로고" />
            </button>
            <Link to="/board">Community</Link>
            <Link to="/comingsoon">Box</Link>
            <Link to="/comingsoon">Wod Record</Link>
            <Link to="/comingsoon">Rank</Link>
          </Tab>
          {isLogin ? (
            <LoginUser onClick={handleModal}>
              <BiSolidUser size={25} color={COLOR.main_yellow} />
            </LoginUser>
          ) : (
            <User>
              <Link to="/login">로그인</Link>
              <Link to="/signup">회원가입</Link>
            </User>
          )}
        </Contain>
      </Wrap>
      <Position />
    </>
  );
};

export default Header;

const Wrap = styled(Container)`
  position: fixed;
  z-index: 50;
  border-bottom: 1px solid ${COLOR.border_grey};

  button {
    cursor: pointer;
    border: none;
    background-color: transparent;
  }

  a {
    margin-left: 14px;
  }
`;

const Contain = styled(Content)`
  height: 60px;
  background-color: black;
  letter-spacing: 0.5px;
  justify-content: space-between;
`;

const Tab = styled.div`
  display: flex;
  align-items: center;
  width: fit-content;
`;
const Logo = styled.img`
  width: 85px;
`;
const User = styled.div`
  display: flex;
  align-items: center;
  width: fit-content;
`;

const LoginUser = styled.div`
  z-index: 150;
  cursor: pointer;
  width: 40px;
  height: 40px;
  border-radius: 50%;
  overflow: hidden;
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: ${COLOR.btn_grey};
`;

const Position = styled.div`
  position: relative;
  width: 100%;
  background-color: black;
  height: 60px;
`;
