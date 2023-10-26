import styled from "styled-components";
import { useState, useEffect, useRef } from "react";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { FiSearch, FiThumbsUp } from "react-icons/fi";
import { BsEye, BsChevronDown, BsChevronUp } from "react-icons/bs";
import { GoComment } from "react-icons/go";
import { BiSolidUser } from "react-icons/bi";
import Pagination from "../../components/board/Pagination";
import { getLists, getListsWithSearch } from "../../apis/api";
import { Link } from "react-router-dom";
import { toast } from "react-toastify";
import { loginState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";

const List = () => {
  const setLogin = useSetRecoilState(loginState);
  const [openOrderBy, setOpenOrderBy] = useState(false);
  const [openSearch, setOpenSearch] = useState(false);
  const [orderBy, setOrderBy] = useState("latest");
  const [searchWord, setSearchWord] = useState("");
  const [searchSort, setSearchSort] = useState("all");
  const [currentPage, setCurrentPage] = useState(1);
  const [totalPages, setTotalPages] = useState(1);
  const [boardData, setBoardData] = useState([]);

  const previousPage = useRef(1);

  const getOrderMessage = () => {
    if (orderBy === "latest") {
      return "최신순";
    } else if (orderBy === "view-count") {
      return "조회순";
    } else if (orderBy === "like-count") {
      return "좋아요순";
    }
  };

  const getSearchSort = () => {
    if (searchSort === "all") {
      return "전체";
    } else if (searchSort === "post-title") {
      return "제목";
    } else if (searchSort === "post-body") {
      return "내용";
    } else if (searchSort === "nickname") {
      return "작성자";
    }
  };

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

  const handlePaginationClick = (pageNumber) => {
    if (pageNumber === previousPage.current) {
      return;
    }

    setCurrentPage(pageNumber);
  };

  const handleSearchClick = () => {
    const fetchListData = async () => {
      try {
        const sortCondition = orderBy;
        const page = currentPage - 1;
        const searchType = searchSort;
        const searchKeyword = searchWord;

        const res = await getListsWithSearch(
          searchKeyword,
          searchType,
          sortCondition,
          page
        );
        setBoardData(res.data);
        setTotalPages(res.pageInfo.totalPages);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchListData();
  };

  useEffect(() => {
    previousPage.current = currentPage;
  }, [currentPage]);

  useEffect(() => {
    const fetchListData = async () => {
      try {
        const sortCondition = orderBy;
        const page = currentPage - 1;

        const res = await getLists(sortCondition, page);
        setBoardData(res.data);
        setTotalPages(res.pageInfo.totalPages);
      } catch (error) {
        if (error && error.status === 401) {
          toast.warning("로그인이 필요합니다");
          setLogin(false);
          localStorage.removeItem("accessToken");
          localStorage.removeItem("refreshToken");
          localStorage.removeItem("accessTokenExpiration");
          localStorage.removeItem("profileImage");
        }
      }
    };

    fetchListData();
  }, [orderBy, currentPage, setLogin]);

  return (
    <Container>
      <Contain>
        <Option>
          <Sort>
            <span onClick={() => setOpenOrderBy((prev) => !prev)}>
              <p>{getOrderMessage()}</p>
              {openOrderBy ? (
                <BsChevronUp color={COLOR.main_grey} />
              ) : (
                <BsChevronDown color={COLOR.main_grey} />
              )}
            </span>
            {openOrderBy ? (
              <SortBtn>
                <div>
                  <button
                    onClick={() => {
                      setOrderBy("latest");
                      setOpenOrderBy(false);
                    }}
                  >
                    최신순
                  </button>
                </div>
                <div>
                  <button
                    onClick={() => {
                      setOrderBy("view-count");
                      setOpenOrderBy(false);
                    }}
                  >
                    조회순
                  </button>
                </div>
                <div>
                  <button
                    onClick={() => {
                      setOrderBy("like-count");
                      setOpenOrderBy(false);
                    }}
                  >
                    좋아요순
                  </button>
                </div>
              </SortBtn>
            ) : null}
          </Sort>
          <Search>
            <Searchfield openSearch={openSearch}>
              <span onClick={() => setOpenSearch((prev) => !prev)}>
                <p>{getSearchSort()}</p>
                {openSearch ? <BsChevronUp /> : <BsChevronDown />}
              </span>
              {openSearch ? (
                <SearchWordBtn>
                  <div>
                    <button
                      onClick={() => {
                        setSearchSort("all");
                        setOpenSearch(false);
                      }}
                    >
                      전체
                    </button>
                  </div>
                  <div>
                    <button
                      onClick={() => {
                        setSearchSort("post-title");
                        setOpenSearch(false);
                      }}
                    >
                      제목
                    </button>
                  </div>
                  <div>
                    <button
                      onClick={() => {
                        setSearchSort("post-body");
                        setOpenSearch(false);
                      }}
                    >
                      내용
                    </button>
                  </div>
                  <div>
                    <button
                      onClick={() => {
                        setSearchSort("nickname");
                        setOpenSearch(false);
                      }}
                    >
                      작성자
                    </button>
                  </div>
                </SearchWordBtn>
              ) : null}
            </Searchfield>
            <SearchWord>
              <input
                name="searchword"
                placeholder="키워드 검색"
                onChange={(e) => {
                  setSearchWord(e.target.value);
                }}
              />
              <SearchBtn onClick={handleSearchClick}>
                <FiSearch color="white" size={24} />
              </SearchBtn>
            </SearchWord>
          </Search>
        </Option>
        <BoardList>
          {boardData &&
            boardData.map((data) => (
              <Link key={data.id} to={`/board/${data.id}`}>
                <ListItem>
                  <Info>
                    <NameAndTime>
                      <Imgbox>
                        {data.writer.profileImageUrl ? (
                          <img src={`${data.writer.profileImageUrl}`} alt="" />
                        ) : (
                          <BiSolidUser size={14} color={COLOR.main_yellow} />
                        )}
                      </Imgbox>
                      <Link to={`/profile/${data.writer.id}`}>
                        {data.writer.nickname}
                      </Link>
                      <p>{formatDate(data.createdDate)}</p>
                    </NameAndTime>
                    <span>
                      <p>
                        <BsEye />
                        {data.viewCount}
                      </p>
                      <p>
                        <GoComment />
                        {data.commentCount}
                      </p>
                    </span>
                  </Info>
                  <TitleAndTag>
                    <Title>{data.title}</Title>
                    <span>
                      <Tag>{/* <p>{data.category}</p> */}</Tag>
                      <p>
                        <FiThumbsUp />
                        <span>{data.likeCount}</span>
                      </p>
                    </span>
                  </TitleAndTag>
                </ListItem>
              </Link>
            ))}
        </BoardList>
        <WriteBtn>
          <Link to="/board/write">작성</Link>
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
  position: relative;
  flex-direction: column;
  justify-content: flex-start;

  button {
    cursor: pointer;
  }
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
    props.$openSearch ? "2px solid white" : "transparent"};

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

const SearchBtn = styled.button`
  background-color: transparent;
  height: 24px;
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
    width: 6rem;
    height: 2rem;
    font-size: 1.1rem;
    display: flex;
    justify-content: center;
    align-items: center;
    color: black;
    background-color: ${COLOR.main_grey};
    border-radius: 5px;

    &:hover {
      background-color: ${COLOR.main_yellow};
    }
    &:active {
      background-color: ${COLOR.active_yellow};
    }
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
  display: flex;
  justify-content: center;
  align-items: center;
  background-color: grey;
  border-radius: 50%;
  overflow: hidden;
  margin-right: 4px;

  img {
    width: 20px;
    height: 20px;
    object-fit: contain;
  }

  & > svg {
    margin-right: 0px;
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

  span > p > svg {
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
    background-color: ${COLOR.main_grey};
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
