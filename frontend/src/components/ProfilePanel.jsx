import { useState } from 'react';

export default function ProfilePanel({ profile, skills, onAddSkill, onCreateTransaction, onDeleteSkill, onClose }) {
    const [newSkillName, setNewSkillName] = useState('');
    const [addProficiency, setAddProficiency] = useState('Beginner');
    
    const [offerSkillId, setOfferSkillId] = useState('');
    const [requestSkillId, setRequestSkillId] = useState('');
    
    const handleAddSubmit = (e) => {
        e.preventDefault();
        if (newSkillName) {
            onAddSkill({ userId: profile.id, skillName: newSkillName, proficiency: addProficiency });
            setNewSkillName('');
            setAddProficiency('Beginner');
        }
    };

    const handleOfferSubmit = (e) => { e.preventDefault(); if (offerSkillId) { onCreateTransaction('offering', { userId: profile.id, skillId: offerSkillId }); setOfferSkillId(''); } };
    const handleRequestSubmit = (e) => { e.preventDefault(); if (requestSkillId) { onCreateTransaction('request', { userId: profile.id, skillId: requestSkillId }); setRequestSkillId(''); } };
    
    const proficiencyColor = { 'Beginner': 'bg-blue-500/20 text-blue-300', 'Intermediate': 'bg-green-500/20 text-green-300', 'Expert': 'bg-red-500/20 text-red-300' };

    return (
        <div className="bg-gray-800/50 p-6 rounded-lg shadow-lg border border-gray-700 col-span-full">
            <div className="flex justify-between items-center border-b border-gray-700 pb-3 mb-4">
                <div className="flex items-center gap-4">
                    <h2 className="text-2xl font-semibold">Profile: <span className="text-indigo-400">{profile.username}</span></h2>
                    <div className="flex gap-2">
                        {profile.userBadges && profile.userBadges.map((badge) => (
                            <div key={badge.name} className="relative group">
                                <span className="material-symbols-outlined text-yellow-400 text-3xl cursor-pointer">{badge.icon}</span>
                                <div className="tooltip absolute bottom-full mb-2 w-48 bg-gray-900 text-white text-center text-sm rounded-lg p-2 pointer-events-none transform -translate-x-1/2 left-1/2">
                                    <p className="font-bold">{badge.name}</p>
                                    <p className="text-xs text-gray-400">{badge.description}</p>
                                </div>
                            </div>
                        ))}
                    </div>
                </div>
                <button onClick={onClose} className="text-gray-500 hover:text-white"><span className="material-symbols-outlined">close</span></button>
            </div>
            <div className="grid grid-cols-1 md:grid-cols-3 gap-6">
                <div className="md:col-span-1">
                    <h3 className="text-lg font-semibold mb-2">My Skills</h3>
                    <div className="space-y-2 max-h-64 overflow-y-auto pr-2">
                        {profile.userSkills && profile.userSkills.length > 0 ? profile.userSkills.map(userSkill => (
                            // *** THIS IS THE FIX ***
                            // The key and the delete function now use userSkill.userSkillId
                            <div key={userSkill.userSkillId} className="bg-gray-900 p-3 rounded-md flex justify-between items-center group">
                                <div>
                                    <span>{userSkill.skillName}</span>
                                    <span className={`ml-2 px-2 py-1 text-xs font-semibold rounded-full ${proficiencyColor[userSkill.proficiency]}`}>{userSkill.proficiency}</span>
                                </div>
                                <button onClick={() => onDeleteSkill(userSkill.userSkillId)} className="text-gray-600 hover:text-red-400 transition-colors opacity-0 group-hover:opacity-100">
                                    <span className="material-symbols-outlined">delete</span>
                                </button>
                            </div>
                        )) : <p className="text-gray-500 italic">No skills added yet.</p>}
                    </div>
                </div>
                <div className="md:col-span-2 grid grid-cols-1 sm:grid-cols-2 gap-6">
                    <form onSubmit={handleAddSubmit} className="space-y-3 bg-gray-900 p-4 rounded-md">
                        <h3 className="text-lg font-semibold mb-2">Add Skill to Profile</h3>
                        <input 
                            type="text"
                            value={newSkillName}
                            onChange={e => setNewSkillName(e.target.value)}
                            placeholder="Skill Name (e.g., C++)"
                            required
                            className="w-full bg-gray-700 p-3 rounded border border-gray-600"
                        />
                        <select value={addProficiency} onChange={e => setAddProficiency(e.target.value)} required className="w-full bg-gray-700 p-3 rounded border border-gray-600">
                            <option>Beginner</option>
                            <option>Intermediate</option>
                            <option>Expert</option>
                        </select>
                        <button type="submit" className="w-full bg-gray-600 hover:bg-gray-500 p-2 rounded font-semibold">Add Skill</button>
                    </form>
                    <div className="space-y-4">
                        <form onSubmit={handleOfferSubmit} className="space-y-3 bg-gray-900 p-4 rounded-md">
                            <h3 className="text-lg font-semibold mb-2">Offer a Skill</h3>
                            <select value={offerSkillId} onChange={e => setOfferSkillId(e.target.value)} required className="w-full bg-gray-700 p-3 rounded border border-gray-600">
                                <option value="">-- Select from My Skills --</option>
                                {profile.userSkills?.map(us => <option key={us.userSkillId} value={us.skillId}>{us.skillName}</option>)}
                            </select>
                            <button type="submit" className="w-full bg-green-600 hover:bg-green-700 p-2 rounded font-semibold">Post Offering</button>
                        </form>
                        <form onSubmit={handleRequestSubmit} className="space-y-3 bg-gray-900 p-4 rounded-md">
                            <h3 className="text-lg font-semibold mb-2">Request a Skill</h3>
                            <select value={requestSkillId} onChange={e => setRequestSkillId(e.target.value)} required className="w-full bg-gray-700 p-3 rounded border border-gray-600">
                                <option value="">-- Select from Marketplace --</option>
                                {skills.map(s => <option key={s.id} value={s.id}>{s.name}</option>)}
                            </select>
                            <button type="submit" className="w-full bg-blue-600 hover:bg-blue-700 p-2 rounded font-semibold">Post Request</button>
                        </form>
                    </div>
                </div>
            </div>
        </div>
    );
}
