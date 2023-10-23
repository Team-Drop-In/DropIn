import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { useNavigate, Link } from "react-router-dom";
import { loginState } from "../../atoms/atom";
import { ModalState } from "../../atoms/atom";
import { useSetRecoilState } from "recoil";

const Menu = () => {
  const setLogin = useSetRecoilState(loginState);
  const setModal = useSetRecoilState(ModalState);
  const navigate = useNavigate();

  const handleMyPageClick = () => {
    setModal(false);
  };

  const handleLogout = () => {
    localStorage.removeItem("accessToken");
    localStorage.removeItem("refreshToken");
    localStorage.removeItem("accessTokenExpiration");
    localStorage.removeItem("profileImage");
    setLogin(false);
    setModal(false);
    navigate("/");
  };

  return (
    <Contain>
      <Wrap>
        <Tabmenu>
          <Triangle />
          <Modal>
            <Link to="/mypage" onClick={handleMyPageClick}>
              마이페이지
            </Link>
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
`;

const Wrap = styled(Content)`
  position: relative;
  justify-content: end;
`;

const Tabmenu = styled.div`
  z-index: 100;
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
  cursor: pointer;
`;
