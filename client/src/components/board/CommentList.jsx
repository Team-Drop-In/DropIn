import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { FiThumbsUp } from "react-icons/fi";
import { Link } from "react-router-dom";
import { BiSolidUser } from "react-icons/bi";

const CommentList = ({ commentsData, setCommentsData }) => {
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
      <List>
        {commentsData &&
          commentsData.map((comment, index) => (
            <CommentItem key={comment.id}>
              <Info>
                <User>
                  <Imgbox>
                    {comment.profileImageUrl ? (
                      <img src={`${comment.profileImageUrl}`} alt="" />
                    ) : (
                      <BiSolidUser size={22} color={COLOR.main_yellow} />
                    )}
                  </Imgbox>
                  <NameAndTime>
                    <Link to={`/profile/${comment.writer.id}`}>
                      <span>{comment.writer.nickname}</span>
                    </Link>
                    <p>{formatDate(comment.createdAt)}</p>
                  </NameAndTime>
                </User>
                <LikeAndBtn>
                  <DeleteAndModify>
                    <button>수정</button>
                    <button>삭제</button>
                  </DeleteAndModify>
                  <span>
                    <button>
                      <FiThumbsUp />
                    </button>
                    {comment.likeCommentCount}
                  </span>
                </LikeAndBtn>
              </Info>
              <Content>{comment.body}</Content>
            </CommentItem>
          ))}
      </List>
    </Wrap>
  );
};

export default CommentList;

const Wrap = styled.section`
  width: 100%;
  height: fit-content;
  display: flex;
  flex-direction: column;
  margin-top: 15px;
`;

const List = styled.ul`
  height: fit-content;
`;

const CommentItem = styled.li`
  height: fit-content;
  padding: 8px 8px;
  border-bottom: 1px solid ${COLOR.border_grey};
`;

const Info = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 10px;
`;

const User = styled.div`
  display: flex;
  align-items: center;
`;

const LikeAndBtn = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-end;
  & > span {
    display: flex;
    align-items: center;
    font-size: 0.9rem;
    color: ${COLOR.main_grey};

    button {
      color: ${COLOR.main_grey};
      background-color: transparent;
      margin-left: 5px;
    }
  }
  svg {
    margin-right: 2px;
  }
`;
const DeleteAndModify = styled.div`
  margin-bottom: 3px;

  button {
    font-size: 0.9rem;
    color: ${COLOR.main_grey};
    background-color: transparent;
    margin-left: 5px;
  }
`;

const Imgbox = styled.div`
  width: 30px;
  height: 30px;
  display: flex;
  justify-content: center;
  align-items: center;
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

const NameAndTime = styled.div`
  display: flex;
  flex-direction: column;
  margin-left: 4px;

  span,
  p {
    color: ${COLOR.main_grey};
  }

  p {
    font-size: 0.7rem;
    margin-top: 4px;
  }
`;

const Content = styled.div`
  min-height: 40px;
  height: fit-content;
  margin-top: 15px;
`;
