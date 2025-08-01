import { useState, useEffect, useCallback } from 'react';
import { api } from '../api';
import ProfilePanel from './ProfilePanel';
import DataPanel from './DataPanel';
import RecommendationPanel from './RecommendationPanel';

export default function Dashboard({ user, onLogout }) {
    const [skills, setSkills] = useState([]);
    const [offerings, setOfferings] = useState([]);
    const [requests, setRequests] = useState([]);
    const [recommendations, setRecommendations] = useState([]);
    const [viewingProfile, setViewingProfile] = useState(null);

    const fetchData = useCallback(async () => {
        try {
            const [skillsData, offeringsData, requestsData] = await Promise.all([
                api.get('/skills/all'), api.get('/offerings'), api.get('/requests')
            ]);
            setSkills(skillsData); setOfferings(offeringsData); setRequests(requestsData);
        } catch (error) {
            console.error("Failed to fetch dashboard data:", error);
        }
    }, []);

    useEffect(() => { fetchData(); }, [fetchData]);

    const handleGetRecommendations = useCallback(async () => {
        alert("The Recommendation Engine is temporarily disabled while we focus on core features.");
        setRecommendations([]);
    }, [user.id]);
    
    const handleCreateTransaction = async (type, data) => { await api.post({offering: '/offerings', request: '/requests'}[type], data); await fetchData(); };
    const handleViewProfile = async () => { const profileData = await api.get(`/users/${user.id}`); setViewingProfile(profileData); };
    const handleAddSkillToProfile = async (profileData) => { await api.post('/profile/add-skill', profileData); const updatedProfile = await api.get(`/users/${user.id}`); setViewingProfile(updatedProfile); };
    const handleMarkAsComplete = async (requestId) => { await api.post(`/requests/${requestId}/complete`); await fetchData(); };
    const handleDeleteSkill = async (userSkillId) => {
        await api.delete(`/profile/skill/${userSkillId}`);
        await fetchData();
        const updatedProfile = await api.get(`/users/${user.id}`);
        setViewingProfile(updatedProfile);
    };

    return (
        <div className="min-h-screen bg-gray-900 text-gray-200 p-4 sm:p-6 lg:p-8">
             <header className="flex justify-between items-center mb-12">
                <h1 className="text-3xl font-bold text-transparent bg-clip-text bg-gradient-to-r from-indigo-400 to-purple-500">SkillSwap</h1>
                <div className="flex items-center gap-4">
                    <span className="text-gray-400">Welcome, <strong className="text-indigo-400">{user.username}</strong></span>
                    <button onClick={onLogout} className="bg-gray-700 hover:bg-red-600 px-4 py-2 rounded font-semibold transition-colors">Logout</button>
                </div>
            </header>
            <main className="grid grid-cols-1 lg:grid-cols-3 gap-8">
                <div className="lg:col-span-2 space-y-8">
                    <div className="bg-gray-800/50 p-6 rounded-lg shadow-lg border border-gray-700">
                        <button onClick={handleViewProfile} className="w-full text-lg p-4 bg-indigo-600 hover:bg-indigo-700 rounded font-bold">View & Edit My Profile</button>
                    </div>
                    {viewingProfile && <ProfilePanel profile={viewingProfile} skills={skills} onAddSkill={handleAddSkillToProfile} onCreateTransaction={handleCreateTransaction} onDeleteSkill={handleDeleteSkill} onClose={() => setViewingProfile(null)} />}
                    <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
                        <DataPanel title="Marketplace Offers" items={offerings} type="offering" />
                        <DataPanel title="Marketplace Requests" items={requests} type="request" currentUser={user} onMarkAsComplete={handleMarkAsComplete} />
                    </div>
                </div>
                <div className="lg:col-span-1">
                    <RecommendationPanel onRecommend={handleGetRecommendations} recommendations={recommendations} />
                </div>
            </main>
        </div>
    );
}
