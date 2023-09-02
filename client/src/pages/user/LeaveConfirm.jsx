import { Container, Content } from "../../styles/style";
import { styled } from "styled-components";
import { COLOR } from "../../styles/theme";
import LogoImage from "../../images/logo.svg";
import Button from "../../components/common/Button";

const LeaveConfirm = () => {
  return (
    <Container>
      <Contain>
        <Logo src={LogoImage} alt="로고" className="logo_img" />
        <Text>
          <Title>회원 탈퇴가 완료되었습니다</Title>
          <span>더 좋은 서비스로 다시 찾아뵙겠습니다</span>
        </Text>
        <Button
          text={"메인 페이지로"}
          width={"180px"}
          height={"39px"}
          style={{
            backgroundColor: `${COLOR.main_yellow}`,
          }}
          type="button"
        />
      </Contain>
    </Container>
  );
};

export default LeaveConfirm;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;

  span {
    color: ${COLOR.main_grey};
  }
`;

const Logo = styled.img`
  width: 180px;
  margin-bottom: 25px;
`;

const Text = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  margin-top: 12px;
  margin-bottom: 35px;
`;

const Title = styled.span`
  font-size: 24px;
  margin-bottom: 7px;
`;
