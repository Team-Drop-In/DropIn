import { useRecoilState } from "recoil";
import { toastState } from "../atoms/atom";

const sleep = (ms) => {
  return new Promise((r) => {
    setTimeout(r, ms);
  });
};

export const useToastState = () => useRecoilState(toastState);

export const useToast = () => {
  const [toast, setToast] = useToastState();

  const close = async (duration) => {
    setToast({ type: "dismiss", message: toast.message });
    await sleep(duration);
    setToast({ type: "remove", message: "" });
  };

  const show = ({ message, duration = 2000 }) => {
    if (!(toast.type === "remove")) {
      return;
    }

    setToast({ type: "add", message });
    close(duration);
  };

  return { show };
};
