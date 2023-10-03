import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { FiThumbsUp } from "react-icons/fi";
import { BsEye } from "react-icons/bs";

const MainText = () => {
  return (
    <Wrap>
      <Head>
        <NameAndInfo>
          <User>
            <Imgbox>
              <img src="http://via.placeholder.com/30x30" alt="" />
            </Imgbox>
            닉네임
          </User>
          <ViewAndLike>
            <span>
              <BsEye />
              조회
            </span>
            <button>
              <span>
                <FiThumbsUp />
                좋아요
              </span>
            </button>
          </ViewAndLike>
        </NameAndInfo>
        <TitleAndTime>
          <span>제목</span>
          <p>시간</p>
        </TitleAndTime>
      </Head>
      <Body>본문</Body>
    </Wrap>
  );
};

export default MainText;

const Wrap = styled.section`
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
  display: flex;

  button {
    background-color: transparent;
    color: ${COLOR.main_grey};
    cursor: pointer;
    padding: 0px 0px 0px 6px;
  }
  svg {
    margin-right: 3px;
  }

  span {
    display: flex;
    justify-content: center;
    align-items: center;
  }
`;

const TitleAndTime = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  height: 35px;

  span {
    font-size: 1.4rem;
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
  min-height: 380px;
  height: fit-content;
`;
