import React, { useState, useEffect } from 'react';
import { ResponsiveContainer, BarChart, Bar, XAxis, YAxis, Tooltip, CartesianGrid } from 'recharts';

export default function App() {
  const [summary, setSummary] = useState({ totalCarbon: 0, scope2Total: 0, scope3Total: 0, totalEntries: 0 });
  const [batches, setBatches] = useState([]);
  const [activities, setActivities] = useState([]);
  
  // Form states
  const [activityType, setActivityType] = useState('ELECTRICITY');
  const [quantity, setQuantity] = useState('');
  const [unit, setUnit] = useState('kWh');
  
  const [batchNumber, setBatchNumber] = useState('');
  const [supplierName, setSupplierName] = useState('');
  const [batchQuantity, setBatchQuantity] = useState('');

  const authHeader = 'Basic ' + btoa('admin:Admin123');

  useEffect(() => {
    fetchData();
  }, []);

  const fetchData = async () => {
    try {
      const summaryRes = await fetch('http://localhost:8080/api/reports/summary', {
        method: 'GET',
        headers: {
          'Authorization': authHeader,
          'Content-Type': 'application/json'
        }
      });
      if (summaryRes.ok) {
        setSummary(await summaryRes.json());
      }

      const batchesRes = await fetch('http://localhost:8080/api/batches', {
        method: 'GET',
        headers: {
          'Authorization': authHeader,
          'Content-Type': 'application/json'
        }
      });
      if (batchesRes.ok) {
        setBatches(await batchesRes.json());
      }

      const activitiesRes = await fetch('http://localhost:8080/api/activities', {
        method: 'GET',
        headers: {
          'Authorization': authHeader,
          'Content-Type': 'application/json'
        }
      });
      if (activitiesRes.ok) {
        setActivities(await activitiesRes.json());
      }
    } catch (err) {
      console.error("Error fetching data from backend:", err);
    }
  };

  const handleAddActivity = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch('http://localhost:8080/api/activities', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': authHeader
        },
        body: JSON.stringify({ activityType, quantity: parseFloat(quantity), unit })
      });
      if (res.ok) {
        setQuantity('');
        fetchData();
      }
    } catch (err) {
      console.error("Failed to add activity:", err);
    }
  };

  const handleAddBatch = async (e) => {
    e.preventDefault();
    try {
      const res = await fetch('http://localhost:8080/api/batches', {
        method: 'POST',
        headers: {
          'Content-Type': 'application/json',
          'Authorization': authHeader
        },
        body: JSON.stringify({ batchNumber, supplierName, quantity: parseFloat(batchQuantity) })
      });
      if (res.ok) {
        setBatchNumber('');
        setSupplierName('');
        setBatchQuantity('');
        fetchData();
      }
    } catch (err) {
      console.error("Failed to add batch:", err);
    }
  };

  const exportCSV = () => {
    let csvContent = "data:text/csv;charset=utf-8,ID,Activity Type,Quantity,Unit,Emission (kgCO2e),Date\n";
    activities.forEach(row => {
      csvContent += `${row.id},${row.activityType},${row.quantity},${row.unit},${row.calculatedEmission},${row.activityDate}\n`;
    });
    const encodedUri = encodeURI(csvContent);
    const link = document.createElement("a");
    link.setAttribute("href", encodedUri);
    link.setAttribute("download", "carbon_emissions_report.csv");
    document.body.appendChild(link);
    link.click();
    document.body.removeChild(link);
  };

  return (
    <div className="min-h-screen bg-slate-900 text-slate-100 p-6">
      <div className="max-w-7xl mx-auto space-y-8">
        
        {/* Header */}
        <header className="flex justify-between items-center border-b border-slate-800 pb-4">
          <div>
            <h1 className="text-3xl font-bold tracking-tight text-emerald-400">GreenTier Carbon Platform</h1>
            <p className="text-sm text-slate-400">Enterprise Carbon Accounting & Supply Chain Traceability</p>
          </div>
          <button 
            onClick={exportCSV}
            className="bg-emerald-600 hover:bg-emerald-500 text-white px-4 py-2 rounded-lg font-medium shadow transition"
          >
            Export CSV Audit Report
          </button>
        </header>

        {/* Metrics Overview Cards */}
        <div className="grid grid-cols-1 md:grid-cols-4 gap-6">
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <p className="text-sm font-medium text-slate-400">Total Carbon Emissions</p>
            <p className="text-3xl font-extrabold text-emerald-400 mt-2">
              {summary?.totalCarbon?.toFixed(2) || '0.00'} <span className="text-sm font-normal text-slate-400">kgCO2e</span>
            </p>
          </div>
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <p className="text-sm font-medium text-slate-400">Scope 2 (Electricity)</p>
            <p className="text-3xl font-extrabold text-blue-400 mt-2">
              {summary?.scope2Total?.toFixed(2) || '0.00'} <span className="text-sm font-normal text-slate-400">kgCO2e</span>
            </p>
          </div>
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <p className="text-sm font-medium text-slate-400">Scope 3 (Supply Chain)</p>
            <p className="text-3xl font-extrabold text-amber-400 mt-2">
              {summary?.scope3Total?.toFixed(2) || '0.00'} <span className="text-sm font-normal text-slate-400">kgCO2e</span>
            </p>
          </div>
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <p className="text-sm font-medium text-slate-400">Logged Activities</p>
            <p className="text-3xl font-extrabold text-purple-400 mt-2">{summary?.totalEntries || 0}</p>
          </div>
        </div>

        {/* Emissions Trend Chart */}
        <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
          <h2 className="text-xl font-semibold mb-4 text-slate-200">Emissions Over Time by Activity</h2>
          <div className="h-72 w-full">
            <ResponsiveContainer width="100%" height="100%">
              <BarChart data={activities}>
                <CartesianGrid strokeDasharray="3 3" stroke="#334155" />
                <XAxis dataKey="activityDate" stroke="#94a3b8" />
                <YAxis stroke="#94a3b8" />
                <Tooltip 
                  contentStyle={{ backgroundColor: '#1e293b', borderColor: '#475569', color: '#f8fafc' }} 
                />
                <Bar dataKey="calculatedEmission" fill="#10b981" radius={[4, 4, 0, 0]} />
              </BarChart>
            </ResponsiveContainer>
          </div>
        </div>

        {/* Forms Grid */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          
          {/* Log Emission Activity Form */}
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <h2 className="text-xl font-semibold mb-4 text-slate-200">Log Emission Activity</h2>
            <form onSubmit={handleAddActivity} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Activity Type</label>
                <select 
                  value={activityType} 
                  onChange={(e) => setActivityType(e.target.value)}
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                >
                  <option value="ELECTRICITY">Electricity (Scope 2)</option>
                  <option value="FUEL">Fuel Combustion (Scope 1)</option>
                  <option value="LOGISTICS">Logistics / Freight (Scope 3)</option>
                </select>
              </div>
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Quantity</label>
                <input 
                  type="number" 
                  step="any"
                  value={quantity} 
                  onChange={(e) => setQuantity(e.target.value)} 
                  placeholder="e.g. 500" 
                  required
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                />
              </div>
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Unit</label>
                <input 
                  type="text" 
                  value={unit} 
                  onChange={(e) => setUnit(e.target.value)} 
                  placeholder="kWh, Liters, km" 
                  required
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                />
              </div>
              <button type="submit" className="w-full bg-emerald-600 hover:bg-emerald-500 text-white font-medium py-2.5 rounded-lg transition">
                Record Emission
              </button>
            </form>
          </div>

          {/* Register Supply Batch Form */}
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm">
            <h2 className="text-xl font-semibold mb-4 text-slate-200">Register Supply Chain Batch</h2>
            <form onSubmit={handleAddBatch} className="space-y-4">
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Batch Number</label>
                <input 
                  type="text" 
                  value={batchNumber} 
                  onChange={(e) => setBatchNumber(e.target.value)} 
                  placeholder="BATCH-2026-001" 
                  required
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                />
              </div>
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Supplier Name</label>
                <input 
                  type="text" 
                  value={supplierName} 
                  onChange={(e) => setSupplierName(e.target.value)} 
                  placeholder="EcoMaterials Ltd" 
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                />
              </div>
              <div>
                <label className="block text-xs font-semibold uppercase text-slate-400 mb-1">Quantity (Units)</label>
                <input 
                  type="number" 
                  step="any"
                  value={batchQuantity} 
                  onChange={(e) => setBatchQuantity(e.target.value)} 
                  placeholder="1000" 
                  required
                  className="w-full bg-slate-900 border border-slate-700 rounded-lg p-2.5 text-slate-100 focus:ring-2 focus:ring-emerald-500 outline-none"
                />
              </div>
              <button type="submit" className="w-full bg-blue-600 hover:bg-blue-500 text-white font-medium py-2.5 rounded-lg transition">
                Register Batch
              </button>
            </form>
          </div>

        </div>

        {/* Data Tables */}
        <div className="grid grid-cols-1 md:grid-cols-2 gap-8">
          
          {/* Batches Table */}
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm overflow-x-auto">
            <h2 className="text-xl font-semibold mb-4 text-slate-200">Registered Supply Batches</h2>
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-slate-700 text-slate-400 text-xs uppercase">
                  <th className="py-3 px-2">Batch #</th>
                  <th className="py-3 px-2">Supplier</th>
                  <th className="py-3 px-2">Qty</th>
                  <th className="py-3 px-2">Date</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-700 text-sm">
                {batches.length === 0 ? (
                  <tr><td colSpan="4" className="py-4 text-center text-slate-500">No batches registered yet.</td></tr>
                ) : (
                  batches.map(b => (
                    <tr key={b.id} className="hover:bg-slate-700/50">
                      <td className="py-3 px-2 font-medium">{b.batchNumber}</td>
                      <td className="py-3 px-2 text-slate-300">{b.supplierName}</td>
                      <td className="py-3 px-2 text-slate-300">{b.quantity}</td>
                      <td className="py-3 px-2 text-slate-400">{b.batchDate}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>

          {/* Activities Table */}
          <div className="bg-slate-800 p-6 rounded-xl border border-slate-700 shadow-sm overflow-x-auto">
            <h2 className="text-xl font-semibold mb-4 text-slate-200">Recent Emission Logs</h2>
            <table className="w-full text-left border-collapse">
              <thead>
                <tr className="border-b border-slate-700 text-slate-400 text-xs uppercase">
                  <th className="py-3 px-2">Type</th>
                  <th className="py-3 px-2">Quantity</th>
                  <th className="py-3 px-2">Emissions (kg)</th>
                  <th className="py-3 px-2">Date</th>
                </tr>
              </thead>
              <tbody className="divide-y divide-slate-700 text-sm">
                {activities.length === 0 ? (
                  <tr><td colSpan="4" className="py-4 text-center text-slate-500">No activity logs recorded yet.</td></tr>
                ) : (
                  activities.map(a => (
                    <tr key={a.id} className="hover:bg-slate-700/50">
                      <td className="py-3 px-2 font-medium text-emerald-400">{a.activityType}</td>
                      <td className="py-3 px-2 text-slate-300">{a.quantity} {a.unit}</td>
                      <td className="py-3 px-2 text-slate-300">{a.calculatedEmission?.toFixed(2)}</td>
                      <td className="py-3 px-2 text-slate-400">{a.activityDate}</td>
                    </tr>
                  ))
                )}
              </tbody>
            </table>
          </div>

        </div>

      </div>
    </div>
  );
}