import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { FiSearch } from "react-icons/fi";
import { BsEye, BsChevronDown, BsChevronUp } from "react-icons/bs";
import { FiThumbsUp } from "react-icons/fi";
import { GoComment } from "react-icons/go";
import Pagination from "../../components/board/Pagination";
import { Link } from "react-router-dom";

const List = () => {
  const [openOrderBy, setOpenOrderBy] = useState(false);
  const [openSearch, setOpenSearch] = useState(false);
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
            <span onClick={() => setOpenOrderBy((prev) => !prev)}>
              <p>최신순</p>
              {openOrderBy ? (
                <BsChevronUp color={COLOR.main_grey} />
              ) : (
                <BsChevronDown color={COLOR.main_grey} />
              )}
            </span>
            {openOrderBy ? (
              <SortBtn>
                <div>
                  <button>최신순</button>
                </div>
                <div>
                  <button>추천순</button>
                </div>
                <div>
                  <button>좋아요순</button>
                </div>
              </SortBtn>
            ) : null}
          </Sort>
          <Search>
            <Searchfield openSearch={openSearch}>
              <span onClick={() => setOpenSearch((prev) => !prev)}>
                <p>전체</p>
                {openSearch ? <BsChevronUp /> : <BsChevronDown />}
              </span>
              {openSearch ? (
                <SearchWordBtn>
                  <div>
                    <button>전체</button>
                  </div>
                  <div>
                    <button>제목</button>
                  </div>
                  <div>
                    <button>내용</button>
                  </div>
                  <div>
                    <button>작성자</button>
                  </div>
                </SearchWordBtn>
              ) : null}
            </Searchfield>
            <SearchWord>
              <input placeholder="키워드 검색" />
              <FiSearch color="white" size={24} />
            </SearchWord>
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
                    <BsEye />
                    조회수
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
        <WriteBtn>
          <Link to="/board/writeform">작성하기</Link>
        </WriteBtn>
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
  min-height: calc(100vh - 60px);
  height: fit-content;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
`;

const Option = styled.section`
  width: 100%;
  height: 38px;
  margin: 20px 0px;
  display: flex;
  justify-content: space-between;
  align-items: flex-start;
`;

const Sort = styled.div`
  width: 110px;
  height: fit-content;
  padding: 9px 10px;
  border: 2px solid white;
  border-radius: 10px;
  display: flex;
  flex-direction: column;
  background-color: black;
  z-index: 20;

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
  width: 360px;
  display: flex;
  justify-content: center;
  align-items: flex-start;
  z-index: 20;
`;

const Searchfield = styled.div`
  width: 90px;
  border-radius: 10px;
  border: 2px solid white;
  background-color: black;

  border-radius: 10px 0px 0px 10px;

  p {
    color: white;
  }

  span {
    color: white;
    display: flex;
    justify-content: space-between;
    align-items: center;
    padding: 9px 9px;
  }
`;

const SearchWord = styled.div`
  border: 2px solid white;
  border-radius: 0px 10px 10px 0px;
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 5px 10px;
  border-left: ${(props) =>
    props.openSearch ? "2px solid white" : "transparent"};

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

const SearchWordBtn = styled.div`
  width: 100%;

  & > div {
    margin-bottom: 4px;
    padding: 0px 9px;
  }

  button {
    padding: 5px 0px;
    background-color: transparent;
    border: none;
    color: ${COLOR.main_grey};
  }
`;

const BoardList = styled.ul`
  width: 100%;
  height: fit-content;
  min-height: 69vh;
`;

const WriteBtn = styled.div`
  width: 100%;
  margin-top: 15px;
  display: flex;
  justify-content: flex-end;
  padding: 0px 8px;

  a {
    padding: 5px 8px;
    color: black;
    background-color: ${COLOR.main_grey};
    border-radius: 5px;
  }
`;

const ListItem = styled.li`
  height: 70px;
  display: flex;
  flex-direction: column;
  padding: 11px 8px;
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
    object-fit: contain;
  }
`;

const Info = styled.div`
  span {
    font-size: 1rem;
    line-height: 1.3rem;
  }

  p {
    margin-left: 5px;
    font-size: 0.9rem;
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

  & > p {
    background-color: ${COLOR.btn_grey};
    padding: 2px 5px;
    color: black;
    border-radius: 3px;
    margin-left: 4px;
    font-size: 12px;
  }
`;

const TitleAndTag = styled.div`
  span > p {
    margin-left: 5px;
    font-size: 0.9rem;
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
