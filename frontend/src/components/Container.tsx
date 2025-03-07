import {ReactNode} from "react";

export default function Container({children}: {children: ReactNode}) {
    return (
        <div className="max-w-[1280px] mx-auto">
            {children}
        </div>
    );
}