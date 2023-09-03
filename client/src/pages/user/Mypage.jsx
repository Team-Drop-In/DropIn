import { Link } from "react-router-dom";
import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { AiOutlineRight } from "react-icons/ai";
import Button from "../../components/common/Button";
import MyInfo from "../../components/user/MyInfo";
import ModifyForm from "../../components/user/ModifyForm";

const Mypage = () => {
  return (
    <Container>
      <Contain>
        <h2>내 정보</h2>
        {/* <MyInfo /> */}
        <ModifyForm />
        <ButtonWrapper>
          <Button
            text={"회원수정"}
            type="button"
            width={"100%"}
            height={"39px"}
            style={{
              backgroundColor: ` ${COLOR.main_yellow}`,
            }}
          />
          <StyledLink to="/leave">
            <span>회원탈퇴</span>
            <AiOutlineRight />
          </StyledLink>
        </ButtonWrapper>
      </Contain>
    </Container>
  );
};

export default Mypage;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;

  h2 {
    color: white;
    font-size: 24px;
    margin-bottom: 50px;
  }
`;

const ButtonWrapper = styled.div`
  margin-top: 20px;
  width: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  button {
    margin-bottom: 10px;
  }
  span {
    color: ${COLOR.main_grey};
  }
`;

const StyledLink = styled(Link)`
  display: flex;
  align-items: center;
`;
