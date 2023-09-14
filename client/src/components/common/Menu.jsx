import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { Link } from "react-router-dom";

const Menu = () => {
  const handleLogout = () => {
    //로그아웃 시 실행
  };

  return (
    <Contain>
      <Wrap>
        <Tabmenu>
          <Triangle />
          <Modal>
            <Link to="/mypage">마이페이지</Link>
            <Button onClick={handleLogout}>로그아웃</Button>
          </Modal>
        </Tabmenu>
      </Wrap>
    </Contain>
  );
};

export default Menu;

const Contain = styled(Container)`
  position: absolute;
  z-index: 100;
`;

const Wrap = styled(Content)`
  position: relative;
  margin-top: 60px;
  justify-content: end;
`;

const Tabmenu = styled.div`
  position: relative;
  bottom: 10px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

const Triangle = styled.div`
  margin-right: 11px;
  width: 0;
  height: 0;
  border-bottom: 7px solid ${COLOR.main_grey};
  border-top: 7px solid transparent;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
`;

const Modal = styled.div`
  position: sticky;
  display: flex;
  flex-direction: column;
  color: black;
  justify-content: center;
  align-items: center;
  width: 110px;
  padding: 5px 0px;
  background-color: ${COLOR.main_grey};
  border-radius: 9px;

  > a {
    width: 100%;
    text-align: center;
    border-bottom: 1px solid #b8b8b8;
    padding: 9px 0px;
  }
`;

const Button = styled.button`
  color: black;
  font-size: 16px;
  padding: 7px 0px;
  font-family: "Pretendard-Regular";
  background-color: transparent;
  border: none;
`;
