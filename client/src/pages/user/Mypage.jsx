import { Link } from "react-router-dom";
import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import MyInfo from "../../components/user/MyInfo";
import ModifyForm from "../../components/user/ModifyForm";

const Mypage = () => {
  return (
    <Container>
      <Contain>
        <h2>내 정보</h2>
        {/* <MyInfo /> */}
        <ModifyForm />
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
