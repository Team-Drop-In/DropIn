import styled from "styled-components";
import { COLOR } from "../../styles/theme";
import { GiFemale, GiMale } from "react-icons/gi";
import { AiOutlineQuestion, AiOutlineRight } from "react-icons/ai";
import { BiSolidUser } from "react-icons/bi";
import Button from "../../components/common/Button";
import { Link } from "react-router-dom";

const MyInfo = ({ username, userimgUrl, setChangeInfo, data }) => {
  const genderIcon =
    data.gender === "MALE" ? (
      <GiMale size={20} color={COLOR.gender_blue} />
    ) : data.gender === "FEMALE" ? (
      <GiFemale size={20} color={COLOR.gender_pink} />
    ) : (
      <AiOutlineQuestion size={20} color={COLOR.main_grey} />
    );

  return (
    <>
      <User>
        <Img>
          <div>
            {!userimgUrl ? (
              <BiSolidUser size={90} color={COLOR.main_yellow} />
            ) : (
              <img src={userimgUrl} alt="프로필" />
            )}
          </div>
          <Username>
            <span>{username}</span>
            {genderIcon}
          </Username>
        </Img>
        <Info>
          <div>
            <Label>이메일</Label>
            <span>{data.username}</span>
          </div>
          <div>
            <Label>이름</Label>
            <span>{data.name}</span>
          </div>
          <div>
            <Label>작성한 게시글수</Label>
            <span>5개</span>
          </div>
          <div>
            <Label>작성한 댓글수</Label>
            <span>5개</span>
          </div>
          <div>
            <Label>좋아요한 박스 목록</Label>
            <BoxList>
              <li>온오프짐</li>
              <li>짐박스</li>
              <li>피트니스</li>
            </BoxList>
          </div>
        </Info>
      </User>
      <ButtonWrapper>
        <Button
          text={"회원수정"}
          type="button"
          width={"100%"}
          height={"39px"}
          style={{
            backgroundColor: ` ${COLOR.main_yellow}`,
          }}
          onClick={() => setChangeInfo(true)}
        />
        <StyledLink to="/leave">
          <span>회원탈퇴</span>
          <AiOutlineRight />
        </StyledLink>
      </ButtonWrapper>
    </>
  );
};

export default MyInfo;

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
    display: flex;
    justify-content: center;
    align-items: center;
    background-color: ${COLOR.btn_grey};
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

const ButtonWrapper = styled.div`
  margin-top: 20px;
  width: 350px;
  display: flex;
  flex-direction: column;
  align-items: flex-end;

  button {
    margin-bottom: 10px;
  }
  span {
    color: ${COLOR.main_grey};
  }
`;

const StyledLink = styled(Link)`
  display: flex;
  align-items: center;
`;
