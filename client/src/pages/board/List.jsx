import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { FiSearch } from "react-icons/fi";
import { BsEye, BsChevronDown } from "react-icons/bs";
import { FiThumbsUp } from "react-icons/fi";
import { GoComment } from "react-icons/go";
import Pagination from "../../components/board/Pagination";
import { Link } from "react-router-dom";

const List = () => {
  const [orderBy, setOrderBy] = useState("latest");
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(10);

  const previousPage = useRef(1);

  const handlePaginationClick = (pageNumber) => {
    if (pageNumber === previousPage.current) {
      return;
    }

    setCurrentPage(pageNumber);
  };

  useEffect(() => {
    previousPage.current = currentPage;
  }, [currentPage]);

  return (
    <Container>
      <Contain>
        <Option>
          <Sort>
            <span>
              <p>최신순</p>
              <BsChevronDown color={COLOR.main_grey} />
            </span>
            {/* <SortBtn>
              <div>
                <button>최신순</button>
              </div>
              <div>
                <button>추천순</button>
              </div>
              <div>
                <button>좋아요순</button>
              </div>
            </SortBtn> */}
          </Sort>
          <Search>
            <input placeholder="키워드 검색" />
            <FiSearch color="white" size={24} />
          </Search>
        </Option>
        <BoardList>
          <Link to="/board/1">
            <ListItem>
              <Info>
                <NameAndTime>
                  <Imgbox>
                    <img src="http://via.placeholder.com/20x20" alt="" />
                  </Imgbox>
                  닉네임
                  <p>시간</p>
                </NameAndTime>
                <span>
                  <p>
                    <BsEye /> 조회수
                  </p>
                  <p>
                    <GoComment />
                    댓글
                  </p>
                </span>
              </Info>
              <TitleAndTag>
                <Title>제목</Title>
                <span>
                  <Tag>
                    <p>태그</p>
                    <p>태그</p>
                    <p>태그</p>
                  </Tag>
                  <p>
                    <FiThumbsUp />
                    추천
                  </p>
                </span>
              </TitleAndTag>
            </ListItem>
          </Link>
        </BoardList>
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPaginationClick={handlePaginationClick}
        />
      </Contain>
    </Container>
  );
};

export default List;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

const Option = styled.section`
  width: 100%;
  height: 38px;
  /* background-color: aliceblue; */
  margin: 20px 0px;
  display: flex;
  justify-content: space-between;
`;

const Sort = styled.div`
  width: 110px;
  height: fit-content;
  padding: 10px;
  border: 2px solid white;
  border-radius: 10px;
  display: flex;
  flex-direction: column;

  span {
    display: flex;
    align-items: center;
    justify-content: space-between;
  }

  p {
    color: ${COLOR.main_grey};
  }
`;

const SortBtn = styled.div`
  margin-top: 6px;

  button {
    padding: 5px 0px;
    background-color: transparent;
    border: none;
    color: ${COLOR.main_grey};
  }
`;

const Search = styled.div`
  width: 280px;
  height: 100%;
  border: 2px solid white;
  border-radius: 10px;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 0px 14px;

  input {
    width: 220px;
    border: none;
    background-color: transparent;
    outline: none;
    color: white;
  }

  input::placeholder {
    color: ${COLOR.main_grey};
    font-weight: 500;
  }
`;

const BoardList = styled.ul`
  width: 100%;
  height: fit-content;
`;

const ListItem = styled.li`
  height: 70px;
  display: flex;
  flex-direction: column;
  padding: 12px 8px;
  justify-content: space-between;
  border-bottom: 1px solid ${COLOR.border_grey};

  & > div {
    display: flex;
    justify-content: space-between;
  }

  span {
    display: flex;
    color: ${COLOR.main_grey};
  }

  span > p {
    display: flex;
    justify-content: center;
    align-items: center;
  }

  p {
    color: ${COLOR.main_grey};
  }
`;

const Imgbox = styled.div`
  width: 20px;
  height: 20px;
  background-color: grey;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 4px;

  img {
    width: 20px;
    height: 20px;
  }
`;

const Info = styled.div`
  span {
    font-size: 1rem;
    line-height: 1.3rem;
  }

  p {
    margin-left: 5px;
  }

  svg {
    margin-right: 2px;
  }
`;

const NameAndTime = styled.span`
  p {
    font-size: 0.8rem;
    line-height: 1.4rem;
  }
`;

const Tag = styled.div`
  display: flex;
`;

const TitleAndTag = styled.div`
  span > p {
    margin-left: 5px;
  }

  p {
    margin-left: 2px;
  }

  svg {
    margin-right: 2px;
  }
`;
const Title = styled.span`
  font-size: 1.1rem;
  line-height: 1rem;
  font-weight: bold;
  letter-spacing: 1px;
`;
