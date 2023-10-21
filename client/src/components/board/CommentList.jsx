import styled from "styled-components";
import { useState, useEffect } from "react";
import { COLOR } from "../../styles/theme";
import { FiThumbsUp } from "react-icons/fi";
import { FaThumbsUp } from "react-icons/fa";
import { Link } from "react-router-dom";
import { BiSolidUser } from "react-icons/bi";
import { updateComment, deleteComment, likeComment } from "../../apis/api";
import { toast } from "react-toastify";

const CommentList = ({ boardId, commentsData, setCommentsData }) => {
  const [isEditing, setIsEditing] = useState(false);
  const [content, setContent] = useState("");
  const formatDate = (createdDate) => {
    const currentDate = new Date();
    const date = new Date(createdDate);
    const timeDifference = currentDate - date;

    if (timeDifference > 24 * 60 * 60 * 1000) {
      // 24시간 이후
      const year = date.getFullYear();
      const month = String(date.getMonth() + 1).padStart(2, "0");
      const day = String(date.getDate()).padStart(2, "0");
      return `${year}-${month}-${day}`;
    } else {
      // 24시간 이내
      const hoursAgo = Math.floor(timeDifference / (60 * 60 * 1000));
      return `${hoursAgo}시간 전`;
    }
  };

  const updateCommentContent = (commentId, newContent) => {
    setCommentsData((prevComments) => {
      const updatedComments = prevComments.map((comment) => {
        if (comment.id === commentId) {
          return {
            ...comment,
            body: newContent,
          };
        }
        return comment;
      });
      return updatedComments;
    });
  };

  const removeCommentById = (commentId) => {
    setCommentsData((prevComments) => {
      const filteredComments = prevComments.filter(
        (comment) => comment.id !== commentId
      );
      return filteredComments;
    });
  };

  const likeCommentCount = (commentId, likeCount) => {
    setCommentsData((prevComments) => {
      const updatedComments = prevComments.map((comment) => {
        if (comment.id === commentId) {
          return {
            ...comment,
            checkCommentLike: true,
            likeCommentCount: likeCount + 1,
          };
        }
        return comment;
      });
      return updatedComments;
    });
  };

  const removelikeCount = (commentId, likeCount) => {
    setCommentsData((prevComments) => {
      const updatedComments = prevComments.map((comment) => {
        if (comment.id === commentId) {
          return {
            ...comment,
            checkCommentLike: false,
            likeCommentCount: likeCount - 1,
          };
        }
        return comment;
      });
      return updatedComments;
    });
  };

  const handleEdit = (commentId, currentContent) => {
    setIsEditing(commentId);
    setContent(currentContent);
  };

  const handleSave = (commentId) => {
    const data = {
      body: content,
    };
    updateComment(boardId, commentId, data)
      .then(() => {
        updateCommentContent(commentId, content);
        setIsEditing(false);
      })
      .catch((error) => {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
        } else {
          toast.warning("댓글 수정 실패");
        }
      });
  };

  const handleButtonDelete = (commentId) => {
    deleteComment(boardId, commentId)
      .then(() => {
        removeCommentById(commentId);
      })
      .catch((error) => {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
        } else {
          toast.warning("댓글 삭제 실패");
        }
      });
  };

  const handleLikeComment = (checkLike, commentId, likeCount) => {
    const data = {
      likeCategoryId: commentId,
      likeCategory: "COMMENT",
    };
    if (!checkLike) {
      likeComment(data)
        .then(() => {
          likeCommentCount(commentId, likeCount);
        })
        .catch((error) => {
          if (error && error.status === 401) {
            toast.warning("로그인이 필요합니다");
          } else {
            toast.warning("댓글 수정 실패");
          }
        });
    } else {
      likeComment(data)
        .then(() => {
          removelikeCount(commentId, likeCount);
        })
        .catch((error) => {
          if (error && error.status === 401) {
            toast.warning("로그인이 필요합니다");
          } else {
            toast.warning("댓글 수정 실패");
          }
        });
    }
  };

  useEffect(() => {
    setContent(commentsData.body);
  }, [commentsData]);

  console.log(commentsData);

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
                  {comment.checkWriter && (
                    <DeleteAndModify>
                      {isEditing === comment.id ? (
                        <button onClick={() => handleSave(comment.id)}>
                          저장
                        </button>
                      ) : (
                        <button
                          onClick={() => handleEdit(comment.id, comment.body)}
                        >
                          수정
                        </button>
                      )}

                      <button onClick={() => handleButtonDelete(comment.id)}>
                        삭제
                      </button>
                    </DeleteAndModify>
                  )}
                  <span>
                    <button
                      onClick={() =>
                        handleLikeComment(
                          comment.checkCommentLike,
                          comment.id,
                          comment.likeCommentCount
                        )
                      }
                    >
                      {comment.checkCommentLike ? (
                        <FaThumbsUp />
                      ) : (
                        <FiThumbsUp />
                      )}
                    </button>
                    {comment.likeCommentCount}
                  </span>
                </LikeAndBtn>
              </Info>
              {isEditing === comment.id ? (
                <>
                  <ModifyForm
                    value={content}
                    onChange={(e) => setContent(e.target.value)}
                  />
                </>
              ) : (
                <Content>{comment.body}</Content>
              )}
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

  button {
    cursor: pointer;
  }
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

const ModifyForm = styled.textarea`
  margin-top: 10px;
  width: 100%;
  min-height: 95px;
  height: fit-content;
  resize: none;
  background-color: white;
  outline: none;
  padding: 8px 8px;
  font-size: 0.9rem;
  border-radius: 5px;
  /* white-space: pre; */
`;
