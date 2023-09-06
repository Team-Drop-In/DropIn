import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import LogoImage from "../../images/logo.svg";
import { useNavigate, Link } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();

  return (
    <Wrap>
      <Contain>
        <Tab>
          <button onClick={() => navigate("/")}>
            <Logo src={LogoImage} alt="로고" className="logo_img" />
          </button>
          <Link to="/">DropIn</Link>
          <Link>커뮤니티</Link>
        </Tab>
        <User>
          <Link to="/login">로그인</Link>
          <Link to="/signup">회원가입</Link>
        </User>
      </Contain>
    </Wrap>
  );
};

export default Header;

const Wrap = styled(Container)`
  position: fixed;
  z-index: 9999;
  border-bottom: 1px solid #2c2c2c;

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
