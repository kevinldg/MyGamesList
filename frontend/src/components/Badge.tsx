export default function Badge({ text }: {text: string;}) {

    const color = () => {
        switch (text) {
            case "COMPLETED":
                return "bg-mgl-green-500";
            case "PLAYING":
                return "bg-mgl-purple-500";
            default:
                return "bg-mgl-dark-500";
        }
    };

    return (
        <span className={`${color()} px-1 py-0.5 text-xs font-bold rounded-xs`}>{text}</span>
    );
}