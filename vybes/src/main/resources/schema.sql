create table vybe (
    id int GENERATED ALWAYS AS IDENTITY primary key,
    songName varchar(50),
    spotifyTrackId varchar(50),
    spotifyArtistIds varchar(50) array,
    spotifyAlbumId varchar(50),
    imageUrl varchar(50),
    postedDate timestamp
);