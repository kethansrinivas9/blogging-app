import React from 'react';

const BlogHomepage = () => {
  // Sample blog post data
  const blogPosts = [
    {
      id: 1,
      title: "The Art of Coffee Making",
      content: "Discover the secrets behind brewing the perfect cup of coffee. From bean selection to grinding techniques...",
      author: "Jane Smith",
      date: "2024-02-06",
      readTime: "5 min"
    },
    {
      id: 2,
      title: "Travel Diaries: Barcelona",
      content: "Exploring the vibrant streets of Barcelona, from Gaudi's masterpieces to hidden tapas bars...",
      author: "John Doe",
      date: "2024-02-05",
      readTime: "8 min"
    },
    {
      id: 3,
      title: "Minimalist Living Guide",
      content: "How to declutter your life and embrace minimalism. Tips and tricks for a more organized space...",
      author: "Alice Johnson",
      date: "2024-02-04",
      readTime: "6 min"
    },
    {
      id: 4,
      title: "Future of AI Technology",
      content: "Exploring the latest developments in artificial intelligence and their impact on our daily lives...",
      author: "Mike Wilson",
      date: "2024-02-03",
      readTime: "10 min"
    },
    {
      id: 5,
      title: "Healthy Breakfast Ideas",
      content: "Start your day right with these nutritious and delicious breakfast recipes that take minutes to prepare...",
      author: "Sarah Lee",
      date: "2024-02-02",
      readTime: "4 min"
    },
    {
      id: 6,
      title: "Photography Basics",
      content: "Learn the fundamentals of photography, from composition to lighting, to capture stunning images...",
      author: "David Chen",
      date: "2024-02-01",
      readTime: "7 min"
    }
  ];

  return (
    <div className="min-h-screen bg-gray-100">
      {/* Header */}
      <header className="bg-white shadow-sm">
        <div className="max-w-7xl mx-auto px-4 py-6">
          <h1 className="text-3xl font-bold text-gray-900">My Blog</h1>
        </div>
      </header>

      {/* Main Content */}
      <main className="max-w-7xl mx-auto px-4 py-8">
        <div className="grid grid-cols-1 md:grid-cols-2 lg:grid-cols-3 gap-6">
          {blogPosts.map(post => (
            <div 
              key={post.id} 
              className="bg-white rounded-lg shadow-md overflow-hidden hover:shadow-lg transition-shadow duration-200"
            >
              <div className="p-6">
                <h2 className="text-xl font-semibold line-clamp-2 mb-2">
                  {post.title}
                </h2>
                <div className="flex items-center text-sm text-gray-500 mb-4">
                  <span>{post.author}</span>
                  <span className="mx-2">•</span>
                  <span>{post.readTime}</span>
                </div>
                <p className="text-gray-600 line-clamp-3 mb-4">
                  {post.content}
                </p>
                <button className="text-blue-600 hover:text-blue-800 font-medium">
                  Read More
                </button>
              </div>
            </div>
          ))}
        </div>
      </main>
    </div>
  );
};

export default BlogHomepage;