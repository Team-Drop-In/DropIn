import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { FiSearch } from "react-icons/fi";
const Board = () => {
  return (
    <Container>
      <Contain>
        <Option>
          <Sort>
            <ul>
              <li>최신순</li>
              <li>추천순</li>
              <li>좋아요순</li>
            </ul>
          </Sort>
          <Search>
            <input placeholder="키워드 검색" />
            <FiSearch color="white" size={24} />
          </Search>
        </Option>
      </Contain>
    </Container>
  );
};

export default Board;

const Contain = styled(Content)`
  margin-top: 60px;
  flex-direction: column;
`;

const Option = styled.section`
  width: 100%;
  height: 38px;
  /* background-color: aliceblue; */
  margin: 20px 0px;
  display: flex;
  justify-content: space-between;

  ul {
    width: 130px;
    padding: 10px;
    border: 1.5px solid white;
    border-radius: 10px;
  }

  li {
    padding: 5px 0px;
    border: 1px solid salmon;
    display: flex;
    align-items: center;
    justify-content: space-between;
  }
`;

const Sort = styled.div``;

const Search = styled.div`
  width: 280px;
  height: 100%;
  border: 1.5px solid white;
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
`;
