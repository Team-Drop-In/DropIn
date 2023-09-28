import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { GiMale } from "react-icons/gi";

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
            <Username>
              <span>닉네임</span>
              <GiMale size={20} color={COLOR.gender_blue} />
            </Username>
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
              <BoxList>
                <li>온오프짐</li>
                <li>짐박스</li>
                <li>피트니스</li>
              </BoxList>
            </div>
          </Info>
        </User>
      </Contain>
    </Container>
  );
};

export default Profile;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
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
  align-items: flex-start;
  flex-wrap: wrap;
`;

const Img = styled.div`
  width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  padding: 20px;

  & > div:first-of-type {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    overflow: hidden;
  }

  & > div:first-of-type > img {
    width: 150px;
    height: 150px;
    object-fit: cover;
  }
`;

const Username = styled.div`
  margin-top: 20px;
  display: flex;
  align-items: center;

  span {
    color: ${COLOR.main_grey};
    margin-right: 3px;
  }
`;

const Info = styled.div`
  width: 400px;
  padding: 20px;

  & > div {
    margin-bottom: 25px;
  }

  & > div > span {
    color: ${COLOR.main_grey};
  }
`;

const BoxList = styled.ul`
  display: flex;
  flex-wrap: wrap;

  li {
    margin-right: 7px;
    padding: 7px 12px;
    border-radius: 7px;
    background-color: ${COLOR.btn_grey};
    font-size: 15px;
  }
`;

const Label = styled.div`
  margin-bottom: 10px;
`;
