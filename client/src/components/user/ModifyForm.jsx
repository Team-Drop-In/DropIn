import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { GiMale } from "react-icons/gi";

const ModifyForm = () => {
  return (
    <User>
      <Img>
        <div>
          <img src="http://placehold.it/200" alt="프로필" />
        </div>
        <Username>
          <span>닉네임</span>
          <GiMale size={20} color={COLOR.gender_blue} />
        </Username>
      </Img>
      <Info>
        <div>
          <Label>이메일</Label>
          <span>Test@gmail.com</span>
        </div>
        <div>
          <Label>이름</Label>
          <span>테스트</span>
        </div>
      </Info>
    </User>
  );
};

export default ModifyForm;

const User = styled.section`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  flex-wrap: wrap;
`;

const Img = styled.div`
  width: 400px;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  object-fit: contain;
  padding: 20px;

  & > div:first-of-type {
    width: 150px;
    height: 150px;
    border-radius: 50%;
    overflow: hidden;
  }

  & > div:first-of-type > img {
    width: 150px;
    height: 150px;
    object-fit: cover;
  }
`;

const Username = styled.div`
  margin-top: 20px;
  display: flex;
  align-items: center;

  span {
    color: ${COLOR.main_grey};
    margin-right: 3px;
  }
`;

const Info = styled.div`
  width: 400px;
  padding: 20px;

  & > div {
    margin-bottom: 25px;
  }

  & > div > span {
    color: ${COLOR.main_grey};
  }
`;

const BoxList = styled.ul`
  display: flex;
  flex-wrap: wrap;

  li {
    margin-right: 7px;
    padding: 7px 12px;
    border-radius: 7px;
    background-color: ${COLOR.btn_grey};
    font-size: 15px;
  }
`;

const Label = styled.div`
  margin-bottom: 10px;
`;
