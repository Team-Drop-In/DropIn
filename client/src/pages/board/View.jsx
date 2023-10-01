import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
const View = () => {
  return (
    <Container>
      <Contain>
        <MainText>
          <Head>
            <NameAndInfo>
              <User>
                <Imgbox>
                  <img src="http://via.placeholder.com/30x30" alt="" />
                </Imgbox>
                닉네임
              </User>
              <ViewAndLike>
                <span>조회</span>
                <span>좋아요</span>
              </ViewAndLike>
            </NameAndInfo>
            <TitleAndTime>
              <span>제목</span>
              <p>시간</p>
            </TitleAndTime>
          </Head>
          <Body>
            <div>본문</div>
          </Body>
        </MainText>
      </Contain>
    </Container>
  );
};

export default View;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

const MainText = styled.section`
  display: flex;
  flex-direction: column;
  width: 100%;
  margin-top: 20px;

  span,
  p {
    color: ${COLOR.main_grey};
  }
`;

const Head = styled.div`
  padding-bottom: 10px;
  border-bottom: 1px solid ${COLOR.border_grey};

  & > div {
    padding: 0px 8px;
  }
`;

const NameAndInfo = styled.div`
  height: 40px;
  display: flex;
  justify-content: space-between;
  align-items: center;

  span {
    color: ${COLOR.main_grey};
  }
`;
const User = styled.div`
  display: flex;
  align-items: center;
`;
const Imgbox = styled.div`
  width: 30px;
  height: 30px;
  background-color: grey;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 5px;

  img {
    width: 30px;
    height: 30px;
    object-fit: contain;
  }
`;

const ViewAndLike = styled.div`
  font-size: 0.9rem;
  span {
    padding: 0px 0px 0px 4px;
  }
`;

const TitleAndTime = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 35px;
  background-color: navy;

  span {
    font-size: 1.3rem;
    line-height: 1rem;
    font-weight: bold;
    letter-spacing: 1px;
  }

  p {
    font-size: 0.9rem;
  }
`;

const Body = styled.div`
  padding: 12px 8px;
`;
