import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import LogoImage from "../../images/logo.svg";
import { Link } from "react-router-dom";

const About = () => {
  return (
    <Wrap>
      <Logo>
        <img src={LogoImage} alt="로고" />
      </Logo>
      <Container>
        <Contain>
          <Top>
            <span>DropIn</span>
            <span>Service</span>
          </Top>
          <Contents>
            <Row>
              <Box>
                <BoxInfo>
                  <p>1. Find box</p>
                  <span>
                    당신에게 딱 맞는 박스를 찾아보세요
                    <br />
                    DropIn의 Find Box Service는 당신에게 알맞는 박스를 제공할 것
                    입니다
                  </span>
                </BoxInfo>
                <Move to="/comingsoon"> More {">"}</Move>
              </Box>
              <Box>
                <BoxInfo>
                  <p>2. Communicate </p>
                  <span>
                    다른 크로스핏터들과 소통하고, 운동 파트너를 찾아보세요.
                    <br />
                    DropIn의 Community Service를 이용하여 궁금한 점을 질문하고,
                    팁을 공유하고 그리고 함께 운동할 동료를 찾고 운동하세요.
                  </span>
                </BoxInfo>
                <Move to="/board"> More {">"}</Move>
              </Box>
            </Row>
            <Row>
              <Box>
                <BoxInfo>
                  <p>3. Record</p>
                  <span>
                    오늘의 Wod를 기록하고 얼마나 성장했는지 확인하세요.
                    <br />
                    DropIn의 Record Wod Service는 당신의 성취를 기록해둘 것
                    입니다.
                  </span>
                </BoxInfo>
                <Move to="/comingsoon"> More {">"}</Move>
              </Box>
              <Box>
                <BoxInfo>
                  <p>4. Competition</p>
                  <span>
                    Hero Wod를 수행하고, 다른 크로스핏터들과 경쟁하세요.
                    <br />
                    DropIn의 Rank Service는 Hero Wod를 바탕으로 서비스
                    이용자들과의 <br /> 순위를 경쟁할 수 있습니다.
                  </span>
                </BoxInfo>
                <Move to="/comingsoon"> More {">"}</Move>
              </Box>
            </Row>
          </Contents>
          <Footer>
            <p>
              <span>대표</span> 조현화 | 조지현
            </p>
            <p>
              <span>사업자</span> 등록번호 011-11-11111 | <span>주소 </span>
              서울시 송파구 오금로11길 55-8
            </p>
            <p>
              <span>Tel</span> 02) 1234-5678 | <span>Email </span>
              projectdropinapp@gmail.com
            </p>
            <p>
              <span>©2023. DropIn. All Rights Reserved.</span>
            </p>
          </Footer>
        </Contain>
      </Container>
    </Wrap>
  );
};

export default About;

const Wrap = styled.main`
  width: 100%;
  height: 100%;
  background: center / cover no-repeat
    url("https://images.unsplash.com/photo-1521804906057-1df8fdb718b7?auto=format&fit=crop&q=80&w=2070&ixlib=rb-4.0.3&ixid=M3wxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8fA%3D%3D");
  position: relative;

  &::before {
    content: "";
    background-color: rgba(0, 0, 0, 0.9);
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
  position: relative;
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const Logo = styled.div`
  position: absolute;
  right: 5px;
  top: 5px;
  z-index: 5;
  width: 70px;

  img {
    width: 70px;
    object-fit: contain;
  }
`;

const Footer = styled.footer`
  position: absolute;
  bottom: 15px;
  width: 100%;
  z-index: 5;
  text-align: center;

  p,
  span {
    font-size: 14px;
    color: white;
  }

  p {
    margin-top: 5px;
    font-weight: 400;
  }

  span {
    font-weight: 600;
  }
`;
const Top = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  font-size: 35px;
  span {
    color: white;
    margin-bottom: 4px;
  }
`;
const Contents = styled.div`
  width: 100%;
  margin-top: 40px;
`;

const Row = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  flex-wrap: wrap;
`;

const Box = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  min-height: 180px;
  height: fit-content;
  padding: 10px 0px;
  width: 45%;
  border-top: 1px solid ${COLOR.btn_grey};

  p {
    color: white;
    font-size: 1.1rem;
    margin-bottom: 15px;
  }
  span {
    color: white;
    line-height: 1.5;
  }
`;
const Move = styled(Link)`
  width: 90px;
  height: 30px;
  border-radius: 50px;
  background-color: ${COLOR.main_yellow};
  color: black;
  font-weight: 600;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const BoxInfo = styled.div`
  height: fit-content;
`;
