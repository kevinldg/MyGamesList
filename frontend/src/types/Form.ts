export type FormProps = {
    formType: "login" | "register";
    onSubmit: (username: string, password: string, repeatPassword?: string) => void;
    error: string | null;
};