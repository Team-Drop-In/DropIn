import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const Container = styled.button`
  width: ${(props) => (props.width ? `${props.width}` : "100%")};
  height: ${(props) => (props.height ? `${props.height}` : "35px")};
  border: none;
  color: black;
  font-weight: 500;
  border-radius: ${(props) =>
    props.borderRadius ? `${props.borderRadius}` : "5px"};
  cursor: pointer;
  display: inline-flex;
  font-weight: bold;
  justify-content: center;
  align-items: center;
  font-size: 15px;
  font-family: "Pretendard-Regular";
  background-color: ${COLOR.btn_grey};
`;

const Button = ({ text, width, height, borderRadius, style }) => {
  return (
    <Container
      style={style}
      width={width}
      height={height}
      borderRadius={borderRadius}
    >
      {text}
    </Container>
  );
};

export default Button;
