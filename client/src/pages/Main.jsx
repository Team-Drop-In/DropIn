import styled from "styled-components";
import { SectionsContainer, Section } from "react-fullpage";
import DropIn from "../components/main/Dropin";
import About from "../components/main/About";

const Main = () => {
  const options = {
    activeClass: "active", // the class that is appended to the sections links
    anchors: ["DropIn", "About"], // the anchors for each sections
    arrowNavigation: false, // use arrow keys
    delay: 1200, // the scroll animation speed
    navigation: true, // use dots navigatio
    scrollBar: false, // use the browser default scrollbar
  };

  return (
    <main>
      <SectionsContainer {...options}>
        <Section>
          <DropIn />
        </Section>
        <Section>
          <About />
        </Section>
      </SectionsContainer>
    </main>
  );
};

export default Main;
