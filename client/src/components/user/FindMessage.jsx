import { styled } from "styled-components";
import { COLOR } from "../../styles/theme";
import { Link } from "react-router-dom";

const FindMessage = ({ findword }) => {
  return (
    <Wrap>
      <span>입력하신 이메일로 {findword}를 발송하였습니다</span>
      <span>{findword}로 로그인 후 이용해주세요</span>
      <HomeLink to="/">홈으로 돌아가기</HomeLink>
    </Wrap>
  );
};

export default FindMessage;

const Wrap = styled.div`
  width: 350px;
  height: 240px;
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;

  span {
    color: ${COLOR.main_grey};
    margin-bottom: 10px;
  }
`;

const HomeLink = styled(Link)`
  margin-top: 25px;
  color: ${COLOR.main_yellow};
`;
