import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const Input = ({
  label,
  span,
  type,
  errorMessage,
  value,
  onChange,
  autocomplete,
  style,
  width,
  placeholder,
  ...restProps
}) => {
  return (
    <Container style={style} width={width}>
      {label ? <Label>{label}</Label> : null}
      <InputComponent
        {...restProps}
        type={type}
        value={value}
        onChange={onChange}
        autocomplete={autocomplete}
        placeholder={placeholder}
      />
      {span ? <span>{span}</span> : null}
      {errorMessage ? <AlertMessage>{errorMessage}</AlertMessage> : null}
    </Container>
  );
};

export default Input;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: ${(props) => (props.width ? `${props.width}` : "100%")};

  [type="radio"] {
    display: none;
  }

  [type="radio"] + span {
    display: inline-block;
    height: 39px;
    text-align: center;
    cursor: pointer;
    text-align: center;
    padding: 15px;
    margin-top: 10px;
    background-color: #818181;
  }

  [type="radio"]:checked + span {
    background-color: #113a6b;
    color: #ffffff;
  }
`;

const Label = styled.label`
  margin-bottom: 7px;
`;

const InputComponent = styled.input`
  margin-bottom: 9px;
  border-radius: 5px;
  padding: 10px;
  background-color: ${COLOR.main_grey};
  outline: none;

  &::placeholder {
    color: black;
    font-weight: 500;
  }
`;

const AlertMessage = styled.span`
  margin-bottom: 7px;
`;
