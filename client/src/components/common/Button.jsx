import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const Container = styled.button`
  width: ${(props) => (props.width ? `${props.width}` : "100%")};
  height: ${(props) => (props.height ? `${props.height}` : "35px")};
  border: none;
  color: black;
  font-weight: 500;
  border-radius: 5px;
  cursor: pointer;
  display: inline-flex;
  font-weight: bold;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  background-color: ${COLOR.btn_grey};
`;

const Button = ({ text, width, height, style }) => {
  return (
    <Container style={style} width={width} height={height}>
      {text}
    </Container>
  );
};

export default Button;
