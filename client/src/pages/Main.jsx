import styled from "styled-components";
import { Container, Content } from "../styles/style";
import { COLOR } from "../styles/theme";
import { SectionsContainer, Section } from "react-fullpage";

const Main = () => {
  const options = {
    activeClass: "active", // the class that is appended to the sections links
    anchors: ["Intro", "About", "DropIn"], // the anchors for each sections
    arrowNavigation: false, // use arrow keys
    delay: 1200, // the scroll animation speed
    navigation: true, // use dots navigatio
    scrollBar: false, // use the browser default scrollbar
  };

  return (
    <Container>
      <Content>
        <SectionsContainer {...options}>
          <Section>
            <MainSection>1dfsdfsd</MainSection>
          </Section>
          <Section>
            <MainSection>2sdfsedfsdfsd</MainSection>
          </Section>
          <Section>
            <MainSection>3sdfsdfd</MainSection>
          </Section>
        </SectionsContainer>
      </Content>
    </Container>
  );
};

export default Main;

const MainSection = styled(Content)`
  height: calc(100vh - 60px);
  flex-direction: column;
  justify-content: flex-start;
`;
