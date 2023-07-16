import styled from "styled-components";
import { container, content } from "../styles/style";
import LogoImage from "../images/logo.svg";
import { useNavigate, Link } from "react-router-dom";

const Header = () => {
  const navigate = useNavigate();

  return (
    <Container>
      <Content>
        <Tab>
          <button onClick={() => navigate("/")}>
            <img src={LogoImage} alt="로고" className="logo_img" />
          </button>
          <Link>DropIn</Link>
          <Link>커뮤니티</Link>
        </Tab>
        <User>
          <Link>로그인</Link>
          <Link>회원가입</Link>
        </User>
      </Content>
    </Container>
  );
};

export default Header;

const Container = styled(container)`
  position: fixed;
  z-index: 9999;
`;

const Content = styled(content)`
  height: 60px;
  background-color: white;
  justify-content: space-between;
`;

const Tab = styled(content)``;
const User = styled(content)``;
