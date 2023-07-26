import styled from "styled-components";
import { COLOR } from "../../styles/theme";

const Input = ({
  label,
  type,
  errorMessage,
  value,
  onChange,
  autocomplete,
  style,
  placeholder,
  ...restProps
}) => {
  return (
    <Container style={style}>
      {label ? <Label>{label}</Label> : null}
      <InputComponent
        {...restProps}
        type={type}
        value={value}
        onChange={onChange}
        autocomplete={autocomplete}
        placeholder={placeholder}
      />
      {errorMessage ? <AlertMessage>{errorMessage}</AlertMessage> : null}
    </Container>
  );
};

export default Input;

const Container = styled.div`
  display: flex;
  flex-direction: column;
  width: ${(props) => (props.width ? `${props.width}` : "100%")};
`;

const Label = styled.label`
  margin-bottom: 7px;
`;

const InputComponent = styled.input`
  margin-bottom: 12px;
  border-radius: 5px;
  padding: 10px;
  background-color: ${COLOR.main_grey};
  outline: none;

  &::placeholder {
    color: black;
    font-weight: 500;
  }
`;

const AlertMessage = styled.span``;
