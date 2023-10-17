import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { FiThumbsUp } from "react-icons/fi";
import { BsEye } from "react-icons/bs";
import { Link } from "react-router-dom";
import { useRecoilValue } from "recoil";
import { boardDataState } from "../../atoms/atom";

const MainText = () => {
  const boardData = useRecoilValue(boardDataState);
  console.log(boardData);

  const formatDate = (createdDate) => {
    const currentDate = new Date();
    const date = new Date(createdDate);
    const timeDifference = currentDate - date;

    if (timeDifference > 24 * 60 * 60 * 1000) {
      // If older than 24 hours, display in YYYY-MM-DD format
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    } else {
      // If within 24 hours, display in X hours ago format
      const hoursAgo = Math.floor(timeDifference / (60 * 60 * 1000));
      return `${hoursAgo}시간 전`;
    }
  };

  return (
    <Wrap>
      <Head>
        <NameAndInfo>
          <User>
            <Imgbox>
              <img src={`${boardData.profileImageUrl}`} alt="" />
            </Imgbox>
            {/* <Link to={`/profile/${boardData.writer.id}`}> */}
            {boardData.writer.nickname}
            {/* </Link> */}
          </User>
          <ViewAndLike>
            <span>
              <BsEye />
              {boardData.viewCount}
            </span>
            <button>
              <span>
                <FiThumbsUp />
                {boardData.likeCount}
              </span>
            </button>
          </ViewAndLike>
        </NameAndInfo>
        <TitleAndTime>
          <span>{boardData.title}</span>
          <div>
            <p>{formatDate(boardData.createdDate)}</p>
            <ModifyBtn>
              <Link to="/board/edit">수정</Link>|<p>삭제</p>
            </ModifyBtn>
          </div>
        </TitleAndTime>
      </Head>
      <Body>{boardData.body}</Body>
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
    text-align: right;
    padding-right: 4px;
    margin-bottom: 8px;
  }

  & > div {
    display: flex;
    flex-direction: column;
  }
`;

const Body = styled.div`
  padding: 12px 8px;
  min-height: 380px;
  height: fit-content;
`;

const ModifyBtn = styled.div`
  width: 100%;
  display: flex;

  a {
    bottom: 5px;
    width: 2.2rem;
    height: 1.2rem;
    color: ${COLOR.main_grey};
    background-color: transparent;
    font-size: 0.9rem;
    text-align: center;
  }
  p {
    margin-left: 4px;
  }
`;
