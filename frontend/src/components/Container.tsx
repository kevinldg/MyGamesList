import {ReactNode} from "react";

export default function Container({children}: {children: ReactNode}) {
    return (
        <div className="max-w-7xl mx-auto min-h-screen">
            {children}
        </div>
    );
}