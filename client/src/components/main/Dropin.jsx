import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import LogoImage from "../../images/logo.svg";

const DropIn = () => {
  return (
    <Wrap>
      <Container>
        <Contain>
          <Info>
            <LogoBox>
              <Logo src={LogoImage} alt="로고" />
            </LogoBox>
            <Text>
              DropIn이란, 자신이 운동하는 박스를 넘어서 다양한 박스를 체험하며,
              <br />
              여러 크로스핏터들과 함께 운동하는 크로스핏 만의 문화입니다.
              <br /> DropIn App 에서는 크로스핏터들을 위한 다양한 정보들과
              공감대를 형성하며,
              <br />
              기록할 수 있는 서비스 입니다.
            </Text>
          </Info>
          <ImgBox>
            <img
              src={`https://images.unsplash.com/photo-1603233720024-4ee0592a58f9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8JUVDJTk3JUFEJUVCJThGJTg0fGVufDB8fDB8fHww`}
              alt="이미지"
            />
          </ImgBox>
        </Contain>
      </Container>
    </Wrap>
  );
};

export default DropIn;

const Wrap = styled.main`
  width: 100%;
  height: 100%;
  background: center / cover no-repeat
    url("https://images.unsplash.com/photo-1603233720024-4ee0592a58f9?auto=format&fit=crop&q=60&w=500&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxzZWFyY2h8N3x8JUVDJTk3JUFEJUVCJThGJTg0fGVufDB8fDB8fHww");
  position: relative;

  &::before {
    content: "";
    background-color: rgba(0, 0, 0, 0.8);
    -webkit-backdrop-filter: blur(5px);
    backdrop-filter: blur(6px);
    position: absolute;
    top: 0;
    left: 0;
    width: 100%;
    height: 100%;
    z-index: 1;
  }
`;

const Contain = styled(Content)`
  z-index: 5;
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: row;
  flex: 1 1 auto;
  flex-wrap: wrap;
  justify-content: center;
  align-items: center;
`;

const Info = styled.div`
  padding: 0px 50px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const LogoBox = styled.div`
  width: 300px;
  height: 200px;
  margin-bottom: 30px;
`;
const Logo = styled.img`
  width: 300px;
  height: 200px;
  position: relative;
`;
const Text = styled.div`
  line-height: 1.5rem;
  text-align: center;
`;
const ImgBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  aspect-ratio: 5/ 3.2;
  width: 450px;
  margin: 15px 50px;
  overflow: hidden;
  border-radius: 110px 5px / 120px;
  box-shadow: 0px 0px 10px 2px rgba(0, 0, 0, 0.5);
`;
