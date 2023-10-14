import React from "react";
import "./postlist.css";
import {useQuery} from "react-query";
import URL from "./url"
const PostList = () => {
    const fetchData = async () => {
        try {
            const response = await fetch(URL.FETCH_ALL_POSTS);
            if (!response.ok) {
                console.log(response)
                throw new Error('Network response was not ok');
            }
            console.log(response)
            return response.json();
        } catch (error) {
            console.log(error)
            throw new Error('Error fetching data');
        }
    };

    const { data, isLoading, isError } = useQuery("myData", fetchData);

    const loading = "Loading...";
    const coloredLetters = loading.split("").map((letter, index) => (
        <span
            key={index}
            className={index % 2 === 0 ? "text-rose-500 text-4xl" : "text-blue-500 text-5xl"}
        >
            {letter}
        </span>
    ));

    if (isLoading)
        return <div className="flex">{coloredLetters}</div>;

    if (isError)
        return <div className="text-rose-500 text-4xl">Error fetching data</div>;

    return (
        <div className="post-list">
            {data.map((post) => (
                <div key={post.id} className="postk">
                    <img src="https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcTYZxm98EmwJ1v1onwGLXl6sNqt857dqUmEOw&usqp=CAU" alt={`Profile of ${post.username}`} className="profile-pic" />
                    <div className="post-content">
                        <p className="username text-sm text-gray-400">{post.author.firstName} updated their status</p>
                        <p className="post-text">{post.content}</p>
                    </div>
                </div>
            ))}
        </div>
    );
};

export default PostList;
