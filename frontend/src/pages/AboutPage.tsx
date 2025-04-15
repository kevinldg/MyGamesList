import {useEffect, useState} from "react";
import axios from "axios";
import ReactMarkdown from "react-markdown";
import "github-markdown-css/github-markdown.css";

export default function AboutPage() {
    const [content, setContent] = useState("");

    useEffect(() => {
        axios.get("https://raw.githubusercontent.com/kevinldg/MyGamesList/main/README.md")
            .then((response) => setContent(response.data))
            .catch((error) => console.error("Error while fetching the README.md", error));
    }, []);

    return (
        <div className="markdown-body mx-auto p-4">
            <ReactMarkdown>{content}</ReactMarkdown>
        </div>
    );
}