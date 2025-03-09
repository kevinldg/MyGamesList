import axios from "axios";

export const getUser = async () => {
    try {
        const response = await axios.get("/api/user/1deb803f-2a4a-4d69-94ec-a00c14ee3451");
        return response.data;
    } catch (error) {
        console.error("Error fetching user: ", error);
        throw error;
    }
}