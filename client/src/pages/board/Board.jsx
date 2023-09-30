import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { FiSearch } from "react-icons/fi";
import { BsChevronDown } from "react-icons/bs";
import Pagination from "../../components/board/Pagination";
const Board = () => {
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
        <List>
          <ListItem>
            <div>
              <span>
                아이콘 닉네임<p>시간</p>
              </span>
              <span>
                <p>조회수</p>
                <p>추천</p>
              </span>
            </div>
            <div>
              <span>제목</span>
              <span>
                <p>태그</p>
                <p>추천</p>
              </span>
            </div>
          </ListItem>
        </List>
        <Pagination
          currentPage={currentPage}
          totalPages={totalPages}
          onPaginationClick={handlePaginationClick}
        />
      </Contain>
    </Container>
  );
};

export default Board;

const Contain = styled(Content)`
  flex-direction: column;
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

const List = styled.ul`
  width: 100%;
  height: fit-content;
  background-color: gray;
`;

const ListItem = styled.li`
  height: 70px;
  display: flex;
  flex-direction: column;
  padding: 10px 7px;
  justify-content: space-between;
  background-color: navy;
  border-bottom: 1px solid ${COLOR.border_grey};

  & > div {
    display: flex;
    justify-content: space-between;
  }

  span {
    display: flex;
    color: ${COLOR.main_grey};
  }

  p {
    color: ${COLOR.main_grey};
  }
`;
