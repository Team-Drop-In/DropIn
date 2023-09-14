import styled from "styled-components";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";

const Menu = () => {
  return (
    <Contain>
      <Wrap>
        <Tabmenu>
          <Triangle />
          <Modal>하이</Modal>
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
  display: flex;
  flex-direction: column;
  align-items: flex-end;
`;

const Triangle = styled.div`
  margin-right: 9px;
  width: 0;
  height: 0;
  border-bottom: 10px solid ${COLOR.main_grey};
  border-top: 10px solid transparent;
  border-left: 10px solid transparent;
  border-right: 10px solid transparent;
`;
const Modal = styled.div`
  position: sticky;
  width: 110px;
  height: 80px;
  background-color: ${COLOR.main_grey};
  border-radius: 10px;
`;
