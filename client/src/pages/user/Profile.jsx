import { useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import styled from "styled-components";
import { GiFemale, GiMale } from "react-icons/gi";
import { BiSolidUser } from "react-icons/bi";
import { AiOutlineQuestion } from "react-icons/ai";
import { Container, Content } from "../../styles/style";
import { COLOR } from "../../styles/theme";
import { getUserInfo } from "../../apis/api";

const Profile = () => {
  const { userId } = useParams();
  const [userData, setUserData] = useState();
  const [userGender, setUserGender] = useState("");

  const genderIcon =
    userGender === "MALE" ? (
      <GiMale size={20} color={COLOR.gender_blue} />
    ) : userGender === "FEMALE" ? (
      <GiFemale size={20} color={COLOR.gender_pink} />
    ) : (
      <AiOutlineQuestion size={20} color={COLOR.main_grey} />
    );

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        const res = await getUserInfo(userId);
        setUserData(res.data);
        setUserGender(res.data.gender);
      } catch (error) {
        console.error("Error fetching data:", error);
      }
    };

    fetchUserData();
  }, [userId]);

  // console.log(userData);
  return (
    <Container>
      {userData && (
        <Contain>
          <h2>회원 정보</h2>
          <User>
            <Img>
              <div>
                {!userData.profileImageUrl ? (
                  <BiSolidUser size={90} color={COLOR.main_yellow} />
                ) : (
                  <img src={userData.profileImageUrl} alt="프로필" />
                )}
              </div>
              <Username>
                <span>{userData.nickname}</span>
                {genderIcon}
              </Username>
            </Img>
            <Info>
              <div>
                <Label>작성한 게시글수</Label>
                <span>{userData.writePostCount}개</span>
              </div>
              <div>
                <Label>작성한 댓글수</Label>
                <span>{userData.writeCommentCount}개</span>
              </div>
              <div>
                <Label>좋아요한 박스 목록</Label>
                <BoxList>
                  {userData && userData.length > 0 ? (
                    userData.map((item, index) => <li key={index}>{item}</li>)
                  ) : (
                    <p>박스 목록이 없습니다</p>
                  )}
                </BoxList>
              </div>
            </Info>
          </User>
        </Contain>
      )}
    </Container>
  );
};

export default Profile;

const Contain = styled(Content)`
  height: calc(100vh - 60px);
  display: flex;
  flex-direction: column;

  h2 {
    color: white;
    font-size: 24px;
    margin-bottom: 50px;
  }
`;

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
    display: flex;
    justify-content: center;
    align-items: center;
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
