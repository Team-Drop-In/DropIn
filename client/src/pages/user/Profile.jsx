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
          <Info>
            <div>
              <Label>작성한 게시글수</Label>
              <span>5개</span>
            </div>
            <div>
              <Label>작성한 댓글수</Label>
              <span>5개</span>
            </div>
            <div>
              <Label>좋아요한 박스 목록</Label>
              <ul>
                <li>온오프짐</li>
                <li>짐박스</li>
                <li>피트니스</li>
              </ul>
            </div>
          </Info>
        </User>
        <button></button>
      </Contain>
    </Container>
  );
};

export default Profile;

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

const User = styled.section`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-wrap: wrap;
`;

const Img = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  width: 400px;

  div {
    width: 200px;
    height: 200px;
    border-radius: 50%;
    overflow: hidden;
  }
`;

const Info = styled.div`
  width: 400px;

  div {
    margin-bottom: 15px;
  }
`;

const Label = styled.div``;
