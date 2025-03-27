import {ReactNode} from "react";

export default function Container({children}: {children: ReactNode}) {
    return (
        <div className="max-w-320 mx-auto">
            {children}
        </div>
    );
}