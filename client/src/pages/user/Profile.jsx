import styled from "styled-components";
import { Container, Content } from "../../styles/style";

const Profile = () => {
  return (
    <Container>
      <Contain>
        <h2>회원 정보</h2>
        <User>
          <Img>
            <div>
              <img src="http://placehold.it/200" alt="프로필" />
            </div>
            <span>닉네임</span>
          </Img>
          <Info></Info>
        </User>
      </Contain>
    </Container>
  );
};

export default Profile;

const Contain = styled(Content)`
  height: 100vh;
  display: flex;
  flex-direction: column;
`;

const User = styled.section`
  display: flex;
`;

const Img = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const Info = styled.div``;
