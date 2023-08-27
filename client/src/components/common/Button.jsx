import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const Container = styled.button`
  width: ${(props) => (props.width ? `${props.width}` : "100%")};
  height: ${(props) => (props.height ? `${props.height}` : "35px")};
  border: none;
  color: black;
  font-weight: 500;
  margin: ${(props) => (props.margin ? `${props.margin}` : "0")};
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

const Button = ({ text, width, height, borderRadius, margin, style, type }) => {
  return (
    <Container
      type={type}
      style={style}
      width={width}
      height={height}
      borderRadius={borderRadius}
      margin={margin}
    >
      {text}
    </Container>
  );
};

export default Button;
